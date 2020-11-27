package com.elderlink.android.src.status.models;

import com.elderlink.android.src.schedule.model.Schedule;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StatusResponse {

    @SerializedName("result")
    private ArrayList<Status> result;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public ArrayList<Status> getResult() {
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
