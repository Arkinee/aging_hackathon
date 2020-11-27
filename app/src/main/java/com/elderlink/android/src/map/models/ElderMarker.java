package com.elderlink.android.src.map.models;

import com.google.gson.annotations.SerializedName;

public class ElderMarker {

    @SerializedName("idx")
    private int idx;
    @SerializedName("elder_idx")
    private int elder_idx;
    @SerializedName("name")
    private String name;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;

    public String getAddress() {
        return address;
    }

    public int getIdx() {
        return idx;
    }

    public int getElder_idx() {
        return elder_idx;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }
}
