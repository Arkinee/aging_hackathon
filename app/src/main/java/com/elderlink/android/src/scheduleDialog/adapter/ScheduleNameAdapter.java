package com.elderlink.android.src.scheduleDialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elderlink.android.R;
import com.elderlink.android.src.join.models.Station;
import com.elderlink.android.src.scheduleDialog.models.Elder;

import java.util.ArrayList;

public class ScheduleNameAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Elder> mNameList;
    private LayoutInflater mInflater;

    public ScheduleNameAdapter(Context context, ArrayList<Elder> names) {
        this.mContext = context;
        this.mNameList = names;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (mNameList != null) return mNameList.size() - 1;
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return mNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.item_name_spinner, viewGroup, false);
        }

        if (position == getCount()) {
            ((TextView) view.findViewById(R.id.item_option_dialog_spinner_tv_option)).setText("");
            ((TextView) view.findViewById(R.id.item_option_dialog_spinner_tv_option)).setHint(((Elder) getItem(getCount())).getName());
            ((TextView) view.findViewById(R.id.item_option_dialog_spinner_tv_option)).setHintTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }

        if (mNameList != null) {
            String option = mNameList.get(position).getName().concat(" 어르신");
            ((TextView) view.findViewById(R.id.item_option_dialog_spinner_tv_option)).setText(option);
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_name_spinner_drop_down, parent, false);
        }

        String name = mNameList.get(position).getName();
        TextView tvOptionName = (TextView) convertView.findViewById(R.id.item_option_dialog_spinner_tv_option);
        LinearLayout linearOptionDropDown = (LinearLayout) convertView.findViewById(R.id.item_option_dialog_spinner_dropdown_linear);

        tvOptionName.setText(name);

        return convertView;
    }
}
