package com.linkfeeling.android.art.board.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewVerticalItemDecoration extends RecyclerView.ItemDecoration {

    private int offset;

    public RecyclerViewVerticalItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if(position!=0){
            outRect.top = offset;
        }
    }
}