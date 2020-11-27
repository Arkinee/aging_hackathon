package com.elderlink.android.src.map;

import android.content.Context;

import androidx.annotation.NonNull;

import com.elderlink.android.src.map.interfaces.MapActivityView;
import com.elderlink.android.src.map.interfaces.MapRetrofitinterface;
import com.elderlink.android.src.map.models.MapResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elderlink.android.src.ApplicationClass.getRetrofit;

public class MapService {

    Context mContext;
    private MapActivityView mView;

    public MapService(final MapActivityView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    // 어르신 위치 받기
    public void tryGetElderList() {
        final MapRetrofitinterface mapRetrofitinterface = getRetrofit().create(MapRetrofitinterface.class);
        mapRetrofitinterface.tryGetElderList().enqueue(new Callback<MapResponse>() {
            @Override
            public void onResponse(@NonNull Call<MapResponse> call, @NonNull Response<MapResponse> response) {

                final MapResponse mapResponse = response.body();
                if (mapResponse == null) {
                    mView.tryGetElderListFailure("빈 값");
                    return;
                }

                if (mapResponse.isSuccess()) {
                    mView.tryGetElderListSuccess(mapResponse.getResult());
                }
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable t) {
                mView.tryGetElderListFailure("서버 연결 실패");
            }
        });
    }

}
