package com.elderlink.android.src.map.interfaces;

import com.elderlink.android.src.map.models.ElderMarker;
import com.elderlink.android.src.scheduleDialog.models.Elder;

import java.util.ArrayList;

public interface MapActivityView {

    void tryGetElderListSuccess(ArrayList<ElderMarker> results);

    void tryGetElderListFailure(String message);
}
