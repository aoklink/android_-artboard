package com.linkfeeling.android.art.board.core.user.board;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.core.data.ArtBoardDataCenterHolder;

public class LoadingViewWorker {
    private ImageView loadingImageView;
    private TextView loadTipTextView;
    private ViewGroup loadingLayout;
    private boolean loadingNow;

    public LoadingViewWorker(ImageView loadingImageView, TextView loadTipTextView, ViewGroup loadingLayout) {
        this.loadingImageView = loadingImageView;
        this.loadTipTextView = loadTipTextView;
        this.loadingLayout = loadingLayout;
    }

    public void show(){
        loadingNow = true;
        loadingLayout.setVisibility(View.VISIBLE);
        RotateAnimation rotateAnimation = new RotateAnimation(0,359,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setDuration(600);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        loadingImageView.startAnimation(rotateAnimation);
        loadTipTextView.setText(loadTipTextView.getContext().getResources().getText(R.string.loading_tip));
    }

    public void hide(){
        loadingNow = false;
        loadingImageView.clearAnimation();
        loadingLayout.setVisibility(View.GONE);
    }

    public void error(){
        loadingNow = false;
        loadTipTextView.setText(loadTipTextView.getContext().getResources().getText(R.string.loading_error_tip));
        loadingImageView.clearAnimation();
        ((View)loadingImageView.getParent()).setOnClickListener(reloadListener);
    }

    private final View.OnClickListener reloadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!loadingNow){
                ArtBoardDataCenterHolder.getInstance().tryAgain();
                show();
            }
        }
    };


    public boolean isShowing(){
        return loadingLayout.getVisibility()==View.VISIBLE;
    }
}
