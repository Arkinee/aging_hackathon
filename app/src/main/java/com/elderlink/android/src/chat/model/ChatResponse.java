package com.elderlink.android.src.chat.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatResponse {

    @SerializedName("result")
    private ArrayList<Chat> result;
    @SerializedName("isSuccess")
    private boolean isSuccess;

    public ArrayList<Chat> getResult() {
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
