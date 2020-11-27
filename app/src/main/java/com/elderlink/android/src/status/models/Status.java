package com.elderlink.android.src.status.models;

import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("idx")
    private int idx;
    @SerializedName("elder_idx")
    private int elder_idx;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private String state;

    public int getIdx() {
        return idx;
    }

    public int getElder_idx() {
        return elder_idx;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }
}
