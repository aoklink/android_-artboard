package com.linkfeeling.android.art.board.widget;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class UserSportSummaryItemBgProvider implements IBackgroundDrawableProvider {

    private int[] colorArray = new int[]{
            0xFF1A78FF,
            0xFFAB49FC,
            0xFF3FC193,
            0xFFFFA941,
            0xFFFF704E,
            0xFFF54646
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
