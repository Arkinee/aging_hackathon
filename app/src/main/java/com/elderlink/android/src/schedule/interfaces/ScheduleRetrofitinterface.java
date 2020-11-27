package com.elderlink.android.src.schedule.interfaces;

import com.elderlink.android.src.schedule.model.ElderResponse;
import com.elderlink.android.src.schedule.model.ScheduleAddResponse;
import com.elderlink.android.src.schedule.model.ScheduleResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ScheduleRetrofitinterface {

    @GET("/manager/calendar")
    Call<ScheduleResponse> tryGetSchedule();

    @POST("/manager/calendar")
    Call<ScheduleAddResponse> tryPostSchedule(@Body HashMap<String, Object> params);

    @GET("/manager/user")
    Call<ElderResponse> getElder();
}
