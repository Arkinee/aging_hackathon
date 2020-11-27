package com.elderlink.android.src.join.models;

public class Station {

    private String name;
    private int id;

    public Station(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
