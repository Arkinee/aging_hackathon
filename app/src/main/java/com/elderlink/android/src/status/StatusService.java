package com.elderlink.android.src.status;

import android.content.Context;

import androidx.annotation.NonNull;

import com.elderlink.android.src.status.interfaces.StatusRetrofitinterface;
import com.elderlink.android.src.status.interfaces.StatusView;
import com.elderlink.android.src.status.models.StatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elderlink.android.src.ApplicationClass.getRetrofit;

public class StatusService {

    Context mContext;
    private StatusView mView;

    public StatusService(final StatusView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    public void tryGetSchedule() {
        final StatusRetrofitinterface statusRetrofitinterface = getRetrofit().create(StatusRetrofitinterface.class);
        statusRetrofitinterface.getStatus().enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<StatusResponse> call, @NonNull Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();
                if (statusResponse == null) {
                    mView.failure("빈 값");
                    return;
                }

                if (statusResponse.isSuccess()) {
                    mView.success(statusResponse.getResult());
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                mView.failure("서버 연결 실패");
            }
        });
    }

}
