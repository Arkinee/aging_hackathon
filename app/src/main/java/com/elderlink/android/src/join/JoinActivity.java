package com.elderlink.android.src.join;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.elderlink.android.R;
import com.elderlink.android.src.BaseActivity;
import com.elderlink.android.src.join.adapter.JoinAdapter;
import com.elderlink.android.src.join.fragments.FirstFragment;
import com.elderlink.android.src.join.fragments.SecondFragment;
import com.elderlink.android.src.main.MainActivity;

import static com.elderlink.android.src.ApplicationClass.sSharedPreferences;

public class JoinActivity extends BaseActivity {

    private Context mContext;
    private TextView mTvBottom;
    private ProgressBar mProgressBar;
    private ViewPager2 mViewPager;

    private FirstFragment mFirst;
    private SecondFragment mSecond;
    private int mPosition = 0;

    private boolean mDouble = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        mContext = this;

        mTvBottom = findViewById(R.id.join_tv_bottom);
        mProgressBar = findViewById(R.id.progress_horizontal);
        mProgressBar.setProgress(50);

        mFirst = new FirstFragment();
        mSecond = new SecondFragment();
        JoinAdapter joinAdapter = new JoinAdapter(this);
        joinAdapter.addFragment(mFirst);
        joinAdapter.addFragment(mSecond);

        mViewPager = findViewById(R.id.join_viewpager);
        mViewPager.setAdapter(joinAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setUserInputEnabled(false);

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    case 0:
                        mTvBottom.setText(getString(R.string.join_tv_continue));
                        mTvBottom.setBackgroundColor(Color.parseColor("#ab69d5"));
                        mProgressBar.setProgress(50);
                        mPosition = 0;
                        mDouble = false;
                        break;
                    case 1:
                        mTvBottom.setText(getString(R.string.join_tv_complete));
                        mTvBottom.setBackgroundColor(Color.parseColor("#7e34cf"));
                        mPosition = 1;
                        mProgressBar.setProgress(75);
                        mDouble = false;
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mViewPager.setCurrentItem(position);
                }

            }
        });

    }

    public void joinOnClick(View view) {
        switch (view.getId()) {
            case R.id.join_iv_back:
                if (mPosition == 0) finish();
                else if (mPosition == 1) {
                    mViewPager.setCurrentItem(0);
                    mPosition = 0;
                }

                mDouble = false;
                break;
            case R.id.join_tv_bottom:
                if (mDouble) return;
                mDouble = true;

                if (mPosition == 0) {
//                    if(mFirst.isFilled()) {
//                        mViewPager.setCurrentItem(1);
//                    }else{
//                        showCustomToastShort("양식을 입력해주세요");
//                        return;
//                    }
                    mViewPager.setCurrentItem(1);
                } else if (mPosition == 1) {
                    if (mSecond.getName().equals("")) {
                        showCustomToastShort("이름을 입력해주세요.");
                        return;
                    }

                    Intent goMain = new Intent(this, MainActivity.class);
                    sSharedPreferences.edit().putString("name", mSecond.getName()).apply();
                    sSharedPreferences.edit().putBoolean("first", false).apply();
                    startActivity(goMain);
                }
                break;
        }
    }


}
