package com.linkfeeling.android.art.board.core.ui.impl;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.linkfeeling.android.art.board.core.ui.IBackgroundDrawableProvider;
import com.linkfeeling.android.art.board.core.ui.config.BoardUIConfig;

public class UserSportSummaryItemBgProvider implements IBackgroundDrawableProvider {
    private int[] colorArray = new int[]{
            0xff86b7ff,
            0xff9185ff,
            0xff50ceaa,
            0xffffc47d,
            0xffff957c,
            0xffff5d5d
    };

    @Override
    public Drawable provide(String name,int index) {
        return newItem(colorArray[index]);
    }

    private GradientDrawable newItem(int color){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setStroke(BoardUIConfig.getUserSportSummaryItemRadius(),color);
        return gradientDrawable;
    }
}
