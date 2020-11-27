package com.elderlink.android.src.join.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class JoinAdapter extends FragmentStateAdapter {

    private Context mContext;
    private List<Fragment> mJoinList;

    public JoinAdapter(FragmentActivity fa) {
        super(fa);
        mJoinList = new ArrayList<>();
    }

    public void addFragment(Fragment fragment) {
        mJoinList.add(fragment);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if (index == 0)
            return mJoinList.get(0);
        else if (index == 1)
            return mJoinList.get(1);
        else return null;
    }

    public int getRealPosition(int position) {
        return position;
    }
}
