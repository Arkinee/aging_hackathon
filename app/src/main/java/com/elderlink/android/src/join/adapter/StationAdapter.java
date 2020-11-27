package com.elderlink.android.src.join.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elderlink.android.R;
import com.elderlink.android.src.join.models.Station;

import java.util.ArrayList;

public class StationAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Station> mStationList;
    private LayoutInflater mInflater;

    public StationAdapter(Context context, ArrayList<Station> options) {
        this.mContext = context;
        this.mStationList = options;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (mStationList != null) return mStationList.size() - 1;
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return mStationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.item_station_spinner, viewGroup, false);
        }

        if (position == getCount()) {
            ((TextView) view.findViewById(R.id.item_option_dialog_spinner_tv_option)).setText("");
            ((TextView) view.findViewById(R.id.item_option_dialog_spinner_tv_option)).setHint(((Station) getItem(getCount())).getName());
            ((TextView) view.findViewById(R.id.item_option_dialog_spinner_tv_option)).setHintTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }

        if (mStationList != null) {
            String option = mStationList.get(position).getName();
            ((TextView) view.findViewById(R.id.item_option_dialog_spinner_tv_option)).setText(option);
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_station_spinner_drop_down, parent, false);
        }

        String station = mStationList.get(position).getName();
        TextView tvOptionName = (TextView) convertView.findViewById(R.id.item_option_dialog_spinner_dropdown_tv_station);
        LinearLayout linearOptionDropDown = (LinearLayout) convertView.findViewById(R.id.item_option_dialog_spinner_dropdown_linear);

        tvOptionName.setText(station);

        return convertView;
    }
}
