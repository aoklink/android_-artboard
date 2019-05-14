package com.linkfeeling.android.art.board.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * Created on 2019/5/13  10:21
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("all")
public final class WaveView extends View {
    private PaintFlagsDrawFilter mDrawFilter;
    private Paint mWavePaint;
    private Paint mWavePaint1;
    private Paint mWavePaint2;
    @ColorInt
    private int mColor1 = Color.parseColor("#FF1A78FF");
    @ColorInt
    private int mColor2 = Color.parseColor("#CC1A78FF");
    @ColorInt
    private int mColor3 = Color.parseColor("#7F1A78FF");

    private float mOffset1 = 0.0f;
    private float mOffset2 = 0.0f;
    private float mOffset3 = 0.0f;

    private float mSpeed1 = -0.5f;
    private float mSpeed2 = -0.6f;
    private float mSpeed3 = -0.7f;

    private float mEndY = 0.0f;
    private float mEndY1 = 0.0f;
    private float mEndY2 = 0.0f;

    private Random mRandom;

    private float mMeasureHeight;
    private float mMeasureWidth;

    private float mWaveHeight;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        // 初始绘制波纹的画笔
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setColor(mColor1);

        mWavePaint1 = new Paint();
        mWavePaint1.setAntiAlias(true);
        mWavePaint1.setStyle(Paint.Style.FILL);
        mWavePaint1.setColor(mColor2);

        mWavePaint2 = new Paint();
        mWavePaint2.setAntiAlias(true);
        mWavePaint2.setStyle(Paint.Style.FILL);
        mWavePaint2.setColor(mColor3);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mRandom = new Random();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureHeight = getMeasuredHeight();
        mMeasureWidth = getMeasuredWidth();
        mWaveHeight = mMeasureHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 从canvas层面去除绘制时锯齿
//        canvas.setDrawFilter(mDrawFilter);
        for (int i = 0; i < mMeasureWidth; i++) {
            // y = A * sin( wx + b) + h ; A： 浪高； w：周期；b：初相；
            mEndY = (float) (mWaveHeight * Math.sin(5 * Math.PI / mMeasureWidth * i + mOffset1)) + mMeasureHeight / 2;
            //画第一条波浪
            canvas.drawLine(i, mMeasureHeight, i, mEndY, mWavePaint);
//
            //画第二条波浪
            mEndY1 = (float) (mWaveHeight * Math.sin(5 * Math.PI / mMeasureWidth * i + mOffset2)) + mMeasureHeight / 2;
            canvas.drawLine(i, mMeasureHeight, i, mEndY1, mWavePaint1);

            //画第二条波浪
            mEndY2 = (float) (mWaveHeight * Math.sin(5 * Math.PI / mMeasureWidth * i + mOffset3)) + mMeasureHeight / 2;
            canvas.drawLine(i, mMeasureHeight, i, mEndY2, mWavePaint2);
        }

        if (mOffset1 < -Float.MAX_VALUE + 1) {//防止数值超过浮点型的最大值
            mOffset1 = 0;
        }
        mOffset1 += mSpeed1;

        if (mOffset2 < -Float.MAX_VALUE + 1) {//防止数值超过浮点型的最大值
            mOffset2 = 0;
        }
        mOffset2 += mSpeed2;

        if (mOffset3 < -Float.MAX_VALUE + 1) {//防止数值超过浮点型的最大值
            mOffset3 = 0;
        }
        mOffset3 += mSpeed3;

//        mSpeed1 = -(float) Math.random();
//        mSpeed2 = -(float) Math.random();
//        mSpeed3 = -(float) Math.random();

        //刷新
        postInvalidate();
    }

//    private void initParams
}
