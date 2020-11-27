package com.elderlink.android.src.status.interfaces;

import com.elderlink.android.src.status.models.Status;

import java.util.ArrayList;

public interface StatusView {

    void success(ArrayList<Status> result);
    void failure(String message);
}
