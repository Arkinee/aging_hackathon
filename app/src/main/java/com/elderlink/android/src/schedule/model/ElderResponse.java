package com.elderlink.android.src.schedule.model;

import com.elderlink.android.src.scheduleDialog.models.Elder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ElderResponse {

    @SerializedName("result")
    private ArrayList<Elder> result;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public ArrayList<Elder> getResult() {
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
