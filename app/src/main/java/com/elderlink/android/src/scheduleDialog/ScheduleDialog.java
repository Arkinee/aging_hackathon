package com.elderlink.android.src.scheduleDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.elderlink.android.R;
import com.elderlink.android.src.schedule.model.Schedule;

public class ScheduleDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "schedule_add";
    private Context mContext;

    private Schedule mSchedule;

    private TextView mTvName;
    private TextView mTvDay;
    private TextView mTvTime;
    private TextView mTvInfo;

    public ScheduleDialog(Context context, Schedule schedule) {
        this.mContext = context;
        mSchedule = schedule;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_schedule, container);

        TextView tvComplete = (TextView) v.findViewById(R.id.dialog_schedule_add_tv_complete);

        mTvName = v.findViewById(R.id.dialog_schedule_tv_name);
        mTvDay = v.findViewById(R.id.dialog_schedule_tv_calendar);
        mTvTime = v.findViewById(R.id.dialog_schedule_tv_time);
        mTvInfo = v.findViewById(R.id.dialog_schedule_tv_info);

        tvComplete.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvName.setText(mSchedule.getName());
        mTvDay.setText(mSchedule.getDay());
        mTvTime.setText(mSchedule.getTime());
        mTvInfo.setText(mSchedule.getMemo());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_schedule_add_tv_complete:
                dismiss();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
