package com.elderlink.android.src.schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.elderlink.android.R;
import com.elderlink.android.src.BaseActivity;
import com.elderlink.android.src.RecyclerDecoration;
import com.elderlink.android.src.schedule.adapter.ScheduleAdapter;
import com.elderlink.android.src.schedule.interfaces.ScheduleActivityView;
import com.elderlink.android.src.schedule.model.Schedule;
import com.elderlink.android.src.scheduleDialog.ScheduleAddDialog;
import com.elderlink.android.src.scheduleDialog.ScheduleDialog;
import com.elderlink.android.src.scheduleDialog.models.Elder;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleActivity extends BaseActivity implements ScheduleAddDialog.ScheduleAddListener, ScheduleActivityView {

    private Context mContext;
    private boolean mDouble = false;
    private RecyclerView mRvSchedule;

    private ArrayList<Schedule> mScheduleList;
    private ScheduleAdapter mScheduleAdapter;

    private ArrayList<Elder> mElderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mContext = this;
        mScheduleList = new ArrayList<>();
        mElderList = new ArrayList<>();

        mRvSchedule = findViewById(R.id.schedule_rv);
        mRvSchedule.addItemDecoration(new RecyclerDecoration(this, 12));
        mScheduleAdapter = new ScheduleAdapter(this, mScheduleList, new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Schedule schedule = mScheduleList.get(pos);
                final ScheduleDialog dialog = new ScheduleDialog(mContext, schedule);
                dialog.setCancelable(false);
                dialog.show(getSupportFragmentManager(), ScheduleAddDialog.TAG_EVENT_DIALOG);

            }
        });

        mRvSchedule.setAdapter(mScheduleAdapter);

        getElder();

    }

    public void scheduleOnClick(View view) {
        switch (view.getId()) {
            case R.id.schedule_iv_back:
                finish();
                break;
            case R.id.schedule_iv_add:
                if (mDouble) return;
                mDouble = true;

                final ScheduleAddDialog addDialog = new ScheduleAddDialog(this, mElderList);
                addDialog.setCancelable(false);
                addDialog.setDialogListener(this);

                addDialog.show(getSupportFragmentManager(), ScheduleAddDialog.TAG_EVENT_DIALOG);
                break;

        }
    }

    void getSchedule() {
        final ScheduleService service = new ScheduleService(this, this);
        service.tryGetSchedule();
        showProgressDialog();
    }

    void getElder() {
        final ScheduleService service = new ScheduleService(this, this);
        service.getElder();
        showProgressDialog();
    }

    @Override
    public void onAddClicked(int idx, String name, String day, String time, String info) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("elder_idx", idx);
        params.put("visit_time", time);
        params.put("visit_date", day);
        params.put("visit_content", info);

        final ScheduleService service = new ScheduleService(this, this);
        service.tryPostSchedule(params);
        showProgressDialog();

    }

    @Override
    public void getSuccess(ArrayList<Schedule> result) {
        hideProgressDialog();
        mScheduleList.addAll(result);
        mScheduleAdapter.notifyDataSetChanged();

//        for (Schedule schedule : mScheduleList) {
//
//            boolean has = false;
//            for (Elder e : mElderList) {
//                if (e.getId() == schedule.getElder_idx()) has = true;
//            }
//
//            if (!has) mElderList.add(new Elder(schedule.getName(), schedule.getElder_idx()));
//
//        }

//        mElderList.add(new Elder("어르신을 선택해주세요", 0));
    }

    @Override
    public void getElderSuccess(ArrayList<Elder> result) {
        mElderList.addAll(result);
        mElderList.add(new Elder("어르신을 선택해주세요", "", 0, ""));
        getSchedule();
    }

    @Override
    public void success(String message) {
        mScheduleList.clear();
        getSchedule();
    }

    @Override
    public void failure(String message) {
        hideProgressDialog();
        showCustomToastShort(message);
    }
}