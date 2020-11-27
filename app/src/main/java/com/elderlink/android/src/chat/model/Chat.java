package com.elderlink.android.src.chat.model;

import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("name")
    private String name;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("state")
    private String status;
    @SerializedName("user_idx")
    private int userIdx;

    private boolean isMine = false;

    public boolean isMine() {
        return isMine;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public String getName() {
        return name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }
}
