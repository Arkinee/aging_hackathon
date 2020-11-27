package com.elderlink.android.src.status;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.elderlink.android.R;
import com.elderlink.android.src.BaseActivity;
import com.elderlink.android.src.chat.ChatActivity;
import com.elderlink.android.src.status.interfaces.StatusView;
import com.elderlink.android.src.status.models.Status;

import java.util.ArrayList;

public class StatusActivity extends BaseActivity implements StatusView {

    private boolean mDouble = false;

    private ImageView mIv1;
    private ImageView mIv2;
    private ImageView mIv3;
    private ImageView mIv4;

    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elder_status);

        mIv1 = findViewById(R.id.elder_status_iv_1);
        mIv2 = findViewById(R.id.elder_status_iv_2);
        mIv3 = findViewById(R.id.elder_status_iv_3);
        mIv4 = findViewById(R.id.elder_status_iv_4);

        mTv1 = findViewById(R.id.elder_status_tv_1);
        mTv2 = findViewById(R.id.elder_status_tv_2);
        mTv3 = findViewById(R.id.elder_status_tv_3);
        mTv4 = findViewById(R.id.elder_status_tv_4);

        final StatusService service = new StatusService(this, this);
        service.tryGetSchedule();
        showProgressDialog();

    }

    public void statusOnClick(View view) {
        switch (view.getId()) {
            case R.id.elder_status_iv_back:
                finish();
                break;
            case R.id.elder_status_constraint_conversation:
                if (mDouble) return;
                mDouble = true;

                startActivity(new Intent(this, ChatActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDouble = false;
    }

    @Override
    public void success(ArrayList<Status> result) {

        if (result.size() >= 1) {
            Status status = result.get(0);
            if (status.getState().equals("아프다")) {
                mIv1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_hospital));
            } else if (status.getState().equals("졸리다")) {
                mIv1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ico_sleep));
            } else if (status.getState().equals("외출한다")) {
                mIv1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_outdoor));
            } else if (status.getState().equals("배고프다")) {
                mIv1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_hungry));
            }

            mTv1.setText(status.getName());
        }

        if (result.size() >= 2) {
            Status status = result.get(1);
            if (status.getState().equals("아프다")) {
                mIv2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_hospital));
            } else if (status.getState().equals("졸리다")) {
                mIv2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ico_sleep));
            } else if (status.getState().equals("외출한다")) {
                mIv2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_outdoor));
            } else if (status.getState().equals("배고프다")) {
                mIv2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_hungry));
            }

            mTv2.setText(status.getName());
        } else {
            mIv2.setVisibility(View.INVISIBLE);
            mTv2.setVisibility(View.INVISIBLE);
        }

        if (result.size() >= 3) {
            Status status = result.get(2);
            if (status.getState().equals("아프다")) {
                mIv3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_hospital));
            } else if (status.getState().equals("졸리다")) {
                mIv3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ico_sleep));
            } else if (status.getState().equals("외출한다")) {
                mIv3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_outdoor));
            } else if (status.getState().equals("배고프다")) {
                mIv3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_hungry));
            }

            mTv3.setText(status.getName());
        } else {
            mIv3.setVisibility(View.INVISIBLE);
            mTv3.setVisibility(View.INVISIBLE);
        }

        if (result.size() >= 4) {

            mIv4.setVisibility(View.VISIBLE);
            mTv4.setVisibility(View.VISIBLE);

            Status status = result.get(3);
            if (status.getState().equals("아프다")) {
                mIv4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_hospital));
            } else if (status.getState().equals("졸리다")) {
                mIv4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ico_sleep));
            } else if (status.getState().equals("외출한다")) {
                mIv4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_outdoor));
            } else if (status.getState().equals("배고프다")) {
                mIv4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_hungry));
            }

            mTv4.setText(status.getName());
        } else {
            mIv4.setVisibility(View.INVISIBLE);
            mTv4.setVisibility(View.INVISIBLE);
        }

        hideProgressDialog();
    }

    @Override
    public void failure(String message) {
        hideProgressDialog();
        showCustomToastShort(message);
    }
}