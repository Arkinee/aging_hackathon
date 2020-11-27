package com.elderlink.android.src.chat.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatPostResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }
}
