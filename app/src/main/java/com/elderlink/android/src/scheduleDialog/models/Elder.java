package com.elderlink.android.src.scheduleDialog.models;

import com.google.gson.annotations.SerializedName;

public class Elder {

    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("idx")
    private int id;
    @SerializedName("phone")
    private String phone;

    public Elder(String name, String type, int id, String phone) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }
}
