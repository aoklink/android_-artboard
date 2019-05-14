package com.link.feeling.framework.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.link.feeling.framework.utils.data.DisplayUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created on 2019/2/27  10:42
 * chenpan pan.chen@linkfeeling.cn
 */
public final class NumberFontTextView extends AppCompatTextView {

//    private boolean adjustTopForAscent = true;
//    private Paint.FontMetricsInt fontMetricsInt;

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
        super.setTypeface(DisplayUtils.getNumberFont());
    }

    @Override
    public boolean getIncludeFontPadding() {
        return false;
    }

}
