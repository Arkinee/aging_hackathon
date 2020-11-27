package com.elderlink.android.src.scheduleDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.elderlink.android.R;
import com.elderlink.android.src.scheduleDialog.adapter.ScheduleNameAdapter;
import com.elderlink.android.src.scheduleDialog.models.Elder;

import java.util.ArrayList;

public class ScheduleAddDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "schedule_add";
    private ScheduleAddListener mScheduleAddInterface;
    private Context mContext;

    private ArrayList<Elder> mElderList;

    private EditText mEdtDay;
    private EditText mEdtTime;
    private EditText mEdtInfo;

    private Elder mElderChoose;
    private String mSelectedName;
    private int mSelectedId;

    public ScheduleAddDialog(Context context, ArrayList<Elder> list) {
        this.mContext = context;
        mElderList = list;
    }

    public interface ScheduleAddListener {
        void onAddClicked(int idx, String name, String day, String time, String info);
    }

    public void setDialogListener(ScheduleAddListener listener) {
        this.mScheduleAddInterface = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_schedule_add, container);

        TextView tvAdd = (TextView) v.findViewById(R.id.dialog_schedule_add_tv_add);
        Spinner spinnerName = v.findViewById(R.id.dialog_schedule_add_spinner);
        spinnerName.setDropDownVerticalOffset((int) dipToPixels(37));
        ScheduleNameAdapter nameAdapter = new ScheduleNameAdapter(mContext, mElderList);
        spinnerName.setAdapter(nameAdapter);

        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Elder elder = (Elder) parent.getSelectedItem();
                mElderChoose = elder;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mEdtDay = v.findViewById(R.id.dialog_schedule_edt_calendar);
        mEdtTime = v.findViewById(R.id.dialog_schedule_edt_time);
        mEdtInfo = v.findViewById(R.id.dialog_schedule_edt_info);

        tvAdd.setOnClickListener(this);

        spinnerName.setSelection(nameAdapter.getCount());

        return v;
    }

    float dipToPixels(float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_schedule_add_tv_add:
                if(mElderChoose != null || !mElderChoose.getName().equals("")) {
                    mScheduleAddInterface.onAddClicked(mElderChoose.getId(), mElderChoose.getName(), mEdtDay.getText().toString(), mEdtTime.getText().toString(), mEdtInfo.getText().toString());
                }
                dismiss();
                break;
        }
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//    }

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
