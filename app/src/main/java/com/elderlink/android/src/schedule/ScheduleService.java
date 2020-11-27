package com.elderlink.android.src.schedule;

import android.content.Context;

import androidx.annotation.NonNull;

import com.elderlink.android.src.map.interfaces.MapActivityView;
import com.elderlink.android.src.map.interfaces.MapRetrofitinterface;
import com.elderlink.android.src.map.models.MapResponse;
import com.elderlink.android.src.schedule.interfaces.ScheduleActivityView;
import com.elderlink.android.src.schedule.interfaces.ScheduleRetrofitinterface;
import com.elderlink.android.src.schedule.model.ElderResponse;
import com.elderlink.android.src.schedule.model.ScheduleAddResponse;
import com.elderlink.android.src.schedule.model.ScheduleResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elderlink.android.src.ApplicationClass.getRetrofit;

public class ScheduleService {

    Context mContext;
    private ScheduleActivityView mView;

    public ScheduleService(final ScheduleActivityView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    // 방문 일정 조회
    public void tryGetSchedule() {
        final ScheduleRetrofitinterface scheduleRetrofitinterface = getRetrofit().create(ScheduleRetrofitinterface.class);
        scheduleRetrofitinterface.tryGetSchedule().enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleResponse> call, @NonNull Response<ScheduleResponse> response) {

                final ScheduleResponse scheduleResponse = response.body();
                if (scheduleResponse == null) {
                    mView.failure("빈 값");
                    return;
                }

                if (scheduleResponse.isSuccess()) {
                    mView.getSuccess(scheduleResponse.getResult());
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                mView.failure("서버 연결 실패");
            }
        });
    }

    // 방문 일정 등록
    public void tryPostSchedule(HashMap<String, Object> params) {
        final ScheduleRetrofitinterface scheduleRetrofitinterface = getRetrofit().create(ScheduleRetrofitinterface.class);
        scheduleRetrofitinterface.tryPostSchedule(params).enqueue(new Callback<ScheduleAddResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleAddResponse> call, @NonNull Response<ScheduleAddResponse> response) {

                final ScheduleAddResponse scheduleResponse = response.body();
                if (scheduleResponse == null) {
                    mView.failure("빈 값");
                    return;
                }

                if (scheduleResponse.isSuccess()) {
                    mView.success(scheduleResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ScheduleAddResponse> call, Throwable t) {
                mView.failure("서버 연결 실패");
            }
        });
    }

    // 방문 일정 등록
    public void getElder() {
        final ScheduleRetrofitinterface scheduleRetrofitinterface = getRetrofit().create(ScheduleRetrofitinterface.class);
        scheduleRetrofitinterface.getElder().enqueue(new Callback<ElderResponse>() {
            @Override
            public void onResponse(@NonNull Call<ElderResponse> call, @NonNull Response<ElderResponse> response) {

                final ElderResponse elderResponse = response.body();
                if (elderResponse == null) {
                    mView.failure("빈 값");
                    return;
                }

                if (elderResponse.isSuccess()) {
                    mView.getElderSuccess(elderResponse.getResult());
                }
            }

            @Override
            public void onFailure(Call<ElderResponse> call, Throwable t) {
                mView.failure("서버 연결 실패");
            }
        });
    }

}
