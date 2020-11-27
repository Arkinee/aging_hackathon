package com.elderlink.android.src.map.interfaces;

import com.elderlink.android.src.map.models.MapResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MapRetrofitinterface {

    @GET("/manager/location")
    Call<MapResponse> tryGetElderList();
}
