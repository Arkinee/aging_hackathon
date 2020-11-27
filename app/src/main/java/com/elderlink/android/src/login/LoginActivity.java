package com.elderlink.android.src.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.elderlink.android.R;
import com.elderlink.android.src.BaseActivity;
import com.elderlink.android.src.join.JoinActivity;
import com.elderlink.android.src.login.interfaces.goJoin;
import com.elderlink.android.src.main.MainActivity;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import static com.elderlink.android.src.ApplicationClass.sSharedPreferences;

public class LoginActivity extends BaseActivity implements goJoin {

    private SessionCallback mSessionCallBack = new SessionCallback(this, this);
    private Session session;
    private boolean mDouble = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = Session.getCurrentSession();
        session.addCallback(mSessionCallBack);
    }

    public void loginOnClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv_sign_up:
                if (mDouble) return;
                mDouble = true;

                if (sSharedPreferences.getBoolean("first", true)) {
                    startActivity(new Intent(this, JoinActivity.class));
                } else startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.login_constraint_kakao:
                session.open(AuthType.KAKAO_TALK, LoginActivity.this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDouble = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(mSessionCallBack);
    }

    @Override
    public void goJoin(String accessToken, String id) {
        mDouble = false;

        if (sSharedPreferences.getBoolean("first", true)) {
            startActivity(new Intent(this, JoinActivity.class));
        } else startActivity(new Intent(this, MainActivity.class));
    }
}
