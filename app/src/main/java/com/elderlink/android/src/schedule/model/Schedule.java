package com.elderlink.android.src.schedule.model;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("idx")
    private int idx;
    @SerializedName("elder_idx")
    private int elder_idx;
    @SerializedName("name")
    private String name;
    @SerializedName("visit_date")
    private String day;
    @SerializedName("visit_time")
    private String time;
    @SerializedName("visit_content")
    private String memo;

    public String getName() {
        return name;
    }

    public int getIdx() {
        return idx;
    }

    public int getElder_idx() {
        return elder_idx;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getMemo() {
        return memo;
    }
}
