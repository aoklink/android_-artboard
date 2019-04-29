package com.linkfeeling.android.art.board.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created on 2019/2/27  10:42
 * chenpan pan.chen@linkfeeling.cn
 */
public final class NumberFontTextView extends AppCompatTextView {

    public NumberFontTextView(Context context) {
        this(context, null);
    }

    public NumberFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "number_font.ttf"));
    }
}
