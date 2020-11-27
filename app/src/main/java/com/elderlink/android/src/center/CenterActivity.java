package com.elderlink.android.src.center;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.elderlink.android.R;
import com.elderlink.android.src.BaseActivity;
import com.elderlink.android.src.splash.SplashActivity;

public class CenterActivity extends BaseActivity {

    private boolean mDouble = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

    }

    public void centerOnClick(View view){
        switch (view.getId()){
            case R.id.center_iv_go_homepage:
                if(mDouble) return;
                mDouble = true;

                Intent goHomepage = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.miraeseum.or.kr/"));
                startActivity(goHomepage);
                break;
            case R.id.center_iv_go_youtube:
                if(mDouble) return;
                mDouble = true;

                Intent goYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/channel/UCo2z8iYOZCgbQ-SL8lbsmqw"));
                startActivity(goYoutube);
                break;
            case R.id.center_tv_start:
                if(mDouble) return;
                mDouble = true;

                startActivity(new Intent(this, SplashActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDouble = false;
    }
}
