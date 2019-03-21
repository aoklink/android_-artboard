package com.linkfeeling.android.art.board.core.ui.impl;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.linkfeeling.android.art.board.core.ui.IBackgroundDrawableProvider;
import com.linkfeeling.android.art.board.core.ui.config.BoardUIConfig;

public class UserSportBgProvider implements IBackgroundDrawableProvider {
    private ColorPair[] colorPairs = new ColorPair[]{
      new ColorPair(0xff9ac8ff,0xff7aadff),
      new ColorPair(0xffb1aefd,0xff8b7dff),
      new ColorPair(0xff63e3b1,0xff41bea6),
      new ColorPair(0xffffca7e,0xffffbc7b),

      new ColorPair(0xffffa27b,0xffff8d7e),
      new ColorPair(0xffff7e7e,0xffff4e4e),
      new ColorPair(0xffffa079,0xffff8e7e),
            new ColorPair(0xff9ac8ff,0xff7aadff),
    };


    public Drawable provide(String name){
        GradientDrawable gradientDrawable = newCommon();
        ColorPair colorPair = colorPairs[Math.abs(name.hashCode()%colorPairs.length)];
        gradientDrawable.setColors(colorPair.getPair());
        return gradientDrawable;
    }

    private GradientDrawable newCommon(){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setUseLevel(true);
        int radius = BoardUIConfig.getUserBoardRadius();
        gradientDrawable.setCornerRadii(new float[]{radius,radius,radius,radius,0,0,0,0});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        return gradientDrawable;
    }


    private static class ColorPair{
        int startColor;
        int endColor;

        public ColorPair(int startColor, int endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
        }

        public int[] getPair(){
            return new int[]{startColor,endColor};
        }
    }

}
