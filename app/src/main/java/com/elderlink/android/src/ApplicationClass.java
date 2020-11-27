package com.elderlink.android.src;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.elderlink.android.config.XAccessTokenInterceptor;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationClass extends Application {

    //서버 주소
//    public static String BASE_URL = "https://49.50.174.243";
    public static String BASE_URL = "http://15.164.131.119";

    //공용 shared preference
    public static SharedPreferences sSharedPreferences = null;

    // 가격 포멧
    public static DecimalFormat myFormatter = new DecimalFormat("###,###");

    // SharedPreferences 키 값
    public static String TAG = "revelyview";

    // JWT Token 키 값
    public static final String X_ACCESS_TOKEN = "X-ACCESS-TOKEN";
    // FCM Token 키 값
    public static final String FCM_TOKEN = "OMO_FCM_TOKEN";

    //날짜 형식
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    public static SimpleDateFormat HOME_MONTH = new SimpleDateFormat("MM", Locale.KOREA);
    public static SimpleDateFormat HOME_DAY = new SimpleDateFormat("dd", Locale.KOREA);

    // Retrofit 인스턴스
    public static Retrofit retrofit;
    public static Retrofit retrofitForKakao;

    private static ApplicationClass instance;

    public static ApplicationClass getApplicationClassContext() {
        if (instance == null) {
            throw new IllegalStateException("This Application does not inherit com.kakao.GlobalApplication");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (sSharedPreferences == null) {
            sSharedPreferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }

        KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

    public static void getFcmToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.d("로그", "getInstanceId failed", task.getException());
                    return;
                }

                String token = task.getResult().getToken();
//                Log.d(TAG, "fcm: " + token);
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.putString(FCM_TOKEN, token);
                editor.apply();
            }
        });
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(new XAccessTokenInterceptor()) // JWT 자동 헤더 전송
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public class KakaoSDKAdapter extends KakaoAdapter {

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return ApplicationClass.getApplicationClassContext();
                }
            };
        }

        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {

                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[]{AuthType.KAKAO_TALK};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Nullable
                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }
    }

}