package com.elderlink.android.src.status.interfaces;

import com.elderlink.android.src.status.models.StatusResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatusRetrofitinterface {

    @GET("/manager/state")
    Call<StatusResponse> getStatus();

}
