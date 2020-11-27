package com.elderlink.android.src;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerDecoration extends RecyclerView.ItemDecoration {

    private final int divHeight;


    public RecyclerDecoration(Context context, int divHeight) {
        this.divHeight = toDP(context, divHeight);
    }

    private int toDP(Context context, int value){
        return (int) TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)

            outRect.bottom = divHeight;

    }

}
