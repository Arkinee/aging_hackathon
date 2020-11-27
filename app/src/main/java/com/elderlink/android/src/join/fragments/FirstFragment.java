package com.elderlink.android.src.join.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elderlink.android.R;
import com.elderlink.android.src.join.adapter.StationAdapter;
import com.elderlink.android.src.join.models.Station;

import java.util.ArrayList;


public class FirstFragment extends Fragment {

    private EditText mEdtPhone;
    private EditText mEdtPassword;
    private Station mStationChoose;

    public FirstFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_step_1, container, false);

        mEdtPhone = view.findViewById(R.id.join_edt_phone);
        mEdtPassword = view.findViewById(R.id.join_edt_password);

        Spinner stationSpinner = view.findViewById(R.id.join_spinner);
        stationSpinner.setDropDownVerticalOffset((int) dipToPixels(35));

        ArrayList<Station> mStationList = new ArrayList<>();
        mStationList.add(new Station("SKT", 1));
        mStationList.add(new Station("KT", 2));
        mStationList.add(new Station("LG U+", 3));
        mStationList.add(new Station("이동통신사", 0));
        StationAdapter stationAdapter = new StationAdapter(getActivity(), mStationList);

        stationSpinner.setAdapter(stationAdapter);
        stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Station station = (Station) parent.getSelectedItem();
                mStationChoose = station;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    float dipToPixels(float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, getResources().getDisplayMetrics());
    }

    public boolean isFilled() {

        return !mEdtPhone.getText().toString().equals("") && !mEdtPassword.getText().toString().equals("");

    }
}
