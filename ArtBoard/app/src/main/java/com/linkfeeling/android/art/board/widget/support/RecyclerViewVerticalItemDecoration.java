package com.linkfeeling.android.art.board.widget.support;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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