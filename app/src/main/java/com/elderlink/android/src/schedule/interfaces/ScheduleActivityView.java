package com.elderlink.android.src.schedule.interfaces;

import com.elderlink.android.src.schedule.model.Schedule;
import com.elderlink.android.src.scheduleDialog.models.Elder;

import java.util.ArrayList;

public interface ScheduleActivityView {

    void getSuccess(ArrayList<Schedule> result);
    void getElderSuccess(ArrayList<Elder> result);
    void success(String message);
    void failure(String message);
}
