package com.elderlink.android.src.login;

import android.content.Context;
import android.util.Log;

import com.elderlink.android.src.login.interfaces.goJoin;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import static com.elderlink.android.src.ApplicationClass.TAG;

public class SessionCallback implements ISessionCallback {

    private Context mContext;
    private goJoin go;

    public SessionCallback(Context context, goJoin go) {
        this.mContext = context;
        this.go = go;
    }

    //로그인 성공 상태
    @Override
    public void onSessionOpened() {
        requestMe();
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.d(TAG, "OnSessionOpenFailed: " + exception.getMessage());
    }

    public void requestMe() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d(TAG, "세션 닫혀 있음: " + errorResult);
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.d(TAG, "사용자 정보 용청 실패: " + errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {

                UserAccount kakaoAccount = result.getKakaoAccount();
                AccessToken accessToken = Session.getCurrentSession().getTokenInfo();
                String id = String.valueOf(result.getId());
                go.goJoin(accessToken.getAccessToken(), id);
            }
        });
    }
}
