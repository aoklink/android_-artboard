package com.linkfeeling.android.art.board.widget.support;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewGridItemDecoration extends RecyclerView.ItemDecoration {

    private int offsetV;
    private int offsetH;

    public RecyclerViewGridItemDecoration(int offsetV, int offsetH) {
        this.offsetV = offsetV;
        this.offsetH = offsetH;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if(position>=4){
            outRect.top = offsetV;
        }else{
            outRect.top = 0;
        }
        if(position%4<3){
            outRect.right = offsetH;
        }else{
            outRect.right = 0;
        }
    }
}
