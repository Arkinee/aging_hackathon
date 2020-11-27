package com.elderlink.android.src.schedule.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScheduleResponse {

    @SerializedName("result")
    private ArrayList<Schedule> result;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public ArrayList<Schedule> getResult() {
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
