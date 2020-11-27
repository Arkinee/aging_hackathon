package com.elderlink.android.src.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.elderlink.android.src.ApplicationClass.sSharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.elderlink.android.R;
import com.elderlink.android.src.BaseActivity;
import com.elderlink.android.src.map.MapActivity;
import com.elderlink.android.src.schedule.ScheduleActivity;
import com.elderlink.android.src.status.StatusActivity;

public class MainActivity extends BaseActivity {

    private long mOnBackClickTime = 0;
    private boolean mDouble = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = sSharedPreferences.getString("name", "이름");
        ((TextView) findViewById(R.id.main_tv_name)).setText(name.concat(" 돌보미님"));

    }

    public void mainOnClick(View view) {
        switch (view.getId()) {
            case R.id.main_constraint_schedule:
                if (mDouble) return;
                mDouble = true;

                Intent schedule = new Intent(this, ScheduleActivity.class);
                startActivity(schedule);
                break;
            case R.id.main_constraint_map:
                if (mDouble) return;
                mDouble = true;

                Intent map = new Intent(this, MapActivity.class);
                startActivity(map);
                break;
            case R.id.main_constraint_call:
                if (mDouble) return;
                mDouble = true;

                Intent status = new Intent(this, StatusActivity.class);
                startActivity(status);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDouble = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        long curTime = System.currentTimeMillis();
        long gapTime = curTime - mOnBackClickTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            mOnBackClickTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}