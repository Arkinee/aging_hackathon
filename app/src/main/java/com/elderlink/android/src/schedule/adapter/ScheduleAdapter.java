package com.elderlink.android.src.schedule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.elderlink.android.R;
import com.elderlink.android.src.schedule.model.Schedule;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Schedule> mScheduleList;
    private OnItemClickListener mListener = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public ScheduleAdapter(Context context, ArrayList<Schedule> marketsList, OnItemClickListener listener) {
        this.mContext = context;
        this.mScheduleList = marketsList;
        this.mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ConstraintLayout constraint;

        ViewHolder(final View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            tvName = itemView.findViewById(R.id.item_schedule_tv_name);
            constraint = itemView.findViewById(R.id.item_schedule_constraint);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_elder_list, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = mScheduleList.get(position);
        holder.tvName.setText(schedule.getName());

        if (position % 3 == 0) {
            holder.constraint.setBackgroundResource(R.drawable.src_item_elder);
        } else if (position % 3 == 1) {
            holder.constraint.setBackgroundResource(R.drawable.src_item_elder_2);
        } else if (position % 3 == 2) {
            holder.constraint.setBackgroundResource(R.drawable.src_item_elder_3);
        }

    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }

}
