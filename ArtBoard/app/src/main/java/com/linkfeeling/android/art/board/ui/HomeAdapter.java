package com.linkfeeling.android.art.board.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.link.feeling.framework.base.BaseViewHolder;
import com.link.feeling.framework.component.image.LinkImageLoader;
import com.link.feeling.framework.component.image.transformation.CircleTransform;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.link.feeling.framework.utils.ui.ViewUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ColorConstants;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;
import com.linkfeeling.android.art.board.data.bean.OffsetModule;
import com.linkfeeling.android.art.board.widget.WaveView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created on 2019/5/14  10:59
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("all")
public final class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements WaveView.PostOffset {

    private Context mContext;

    private List<HomeRemoteModule> mModules;

    private OffsetModule mCurrentOffset;
    private OffsetModule mInflateOffset;
    private HomeRemoteModule mModule;
    private HomeHolder mHolder;

    private int mRecyclerViewHeight;
    private int mRecyclerViewWidth;

    private SparseArray<HomeRemoteModule> mSparseArray;

    private int DP10 = (int) DisplayUtils.dp2px(10);
    private int DP15 = (int) DisplayUtils.dp2px(15);
    private int DP20 = (int) DisplayUtils.dp2px(20);
    private int DP30 = (int) DisplayUtils.dp2px(30);
    private int DP40 = (int) DisplayUtils.dp2px(40);
    private int DP45 = (int) DisplayUtils.dp2px(45);
    private int DP50 = (int) DisplayUtils.dp2px(50);
    private int DP55 = (int) DisplayUtils.dp2px(55);
    private int DP60 = (int) DisplayUtils.dp2px(60);
    private int DP70 = (int) DisplayUtils.dp2px(70);
    private int DP80 = (int) DisplayUtils.dp2px(80);

    private CircleTransform mCircleTransform;

    private Animation mOfflineAnimation;


    HomeAdapter(Context mContext) {
        this.mContext = mContext;
        mModules = new ArrayList<>();
        mCircleTransform = new CircleTransform();
        mOfflineAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_out);
        mSparseArray = new SparseArray<>();
    }

    void setModules(List<HomeRemoteModule> mModules) {
        this.mModules.clear();
        this.mModules.addAll(mModules);
        notifyDataSetChanged();
    }

    void setRecyclerViewHeight(float mRecyclerViewHeight) {
        this.mRecyclerViewHeight = (int) mRecyclerViewHeight;
    }

    void setRecyclerViewWidth(float mRecyclerViewWidth) {
        this.mRecyclerViewWidth = (int) mRecyclerViewWidth;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item1, parent, false);
        return new HomeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        mModule = mModules.get(position);
        mHolder = (HomeHolder) holder;

        mInflateOffset = HomeActivity.sOffsetCache.get(position);

        mHolder.mWaveView.initValueManager(position, HomeAdapter.this, mInflateOffset.getOffset1(), mInflateOffset.getOffset2(), mInflateOffset.getOffset3(), ColorConstants.loadColors(mModule.getPercent()), mModule.isStatus());


        LinkImageLoader.INSTANCE.load(mModule.getHead_icon(), mHolder.mIvAvatar, mCircleTransform);

        mHolder.mTvName.setText(mModule.getUser_name());

        if (!mHolder.mTvPercent.getText().equals(mModule.getPercentStr())) {
            mHolder.mTvPercent.setText(mModule.getPercentStr());
        }

        mHolder.mClTop.setBackgroundColor(ColorConstants.loadColor(mModule.getPercent()));


        ViewGroup.LayoutParams params = mHolder.mLlRoot.getLayoutParams();
        ViewGroup.LayoutParams rootParams = mHolder.itemView.getLayoutParams();
        ViewGroup.LayoutParams ivParams = mHolder.mIvAvatar.getLayoutParams();

        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mHolder.itemView.getLayoutParams();
        ViewGroup.MarginLayoutParams nameMarginParams = (ViewGroup.MarginLayoutParams) mHolder.mTvName.getLayoutParams();
        ViewGroup.MarginLayoutParams avatarMarginParams = (ViewGroup.MarginLayoutParams) mHolder.mIvAvatar.getLayoutParams();

        ViewUtils.setGone(mHolder.mClMiddle);
        ViewUtils.setGone(mHolder.mClBottom);

        switch (CollectionsUtil.size(mModules)) {
            case 1:
                mHolder.mTvCalorie.setText(mModule.getKc());
                mHolder.mTvBpm.setText(mModule.getHeart_rate());
                ViewUtils.setVisible(mHolder.mClMiddle);
                params.height = (int) (mRecyclerViewHeight * 0.95);
                params.width = (int) (mRecyclerViewWidth * 0.9);
                rootParams.height = MATCH_PARENT;
                marginParams.topMargin = 0;
                nameMarginParams.leftMargin = DP30;
                avatarMarginParams.leftMargin = DP30;
                avatarMarginParams.topMargin = DP30;
                ivParams.height = DP80;
                ivParams.width = DP80;
                mHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
                mHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 88);
                mHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 66);
                mHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 66);

                if (!mHolder.mTvBpm.getText().equals(mModule.getHeart_rate())) {
                    mHolder.mTvBpm.setText(mModule.getHeart_rate());
                }
                if (!mHolder.mTvCalorie.getText().equals(mModule.getKc())) {
                    mHolder.mTvCalorie.setText(mModule.getKc());
                }
                break;
            case 2:
                mHolder.mTvCalorie.setText(mModule.getKc());
                mHolder.mTvBpm.setText(mModule.getHeart_rate());
                ViewUtils.setVisible(mHolder.mClMiddle);
                rootParams.height = MATCH_PARENT;
                marginParams.topMargin = 0;
                params.height = (int) (mRecyclerViewHeight * 0.95);
                params.width = (int) (mRecyclerViewWidth * 0.50);
                nameMarginParams.leftMargin = DP20;
                avatarMarginParams.leftMargin = DP20;
                avatarMarginParams.topMargin = DP30;
                ivParams.height = DP60;
                ivParams.width = DP60;
                mHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                mHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 66);
                mHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                mHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                if (!mHolder.mTvBpm.getText().equals(mModule.getHeart_rate())) {
                    mHolder.mTvBpm.setText(mModule.getHeart_rate());
                }
                if (!mHolder.mTvCalorie.getText().equals(mModule.getKc())) {
                    mHolder.mTvCalorie.setText(mModule.getKc());
                }
                break;
            case 3:
            case 4:
                mHolder.mTvCalorie.setText(mModule.getKc());
                mHolder.mTvBpm.setText(mModule.getHeart_rate());
                ViewUtils.setVisible(mHolder.mClMiddle);
                marginParams.topMargin = DP15;
                rootParams.height = WRAP_CONTENT;
                params.height = (int) (mRecyclerViewHeight * 0.46);
                params.width = (int) (mRecyclerViewWidth * 0.50);
                ivParams.width = DP55;
                ivParams.height = DP55;
                nameMarginParams.leftMargin = DP20;
                avatarMarginParams.leftMargin = DP20;
                avatarMarginParams.topMargin = DP15;
                mHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                mHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 44);
                mHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                mHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                break;
            case 5:
            case 6:
            default:
                ViewUtils.setVisible(mHolder.mClBottom);
                marginParams.topMargin = DP20;
                rootParams.height = WRAP_CONTENT;
                params.height = (int) (mRecyclerViewHeight * 0.47);
                params.width = (int) (mRecyclerViewWidth * 0.23);
                ivParams.width = DP45;
                ivParams.height = DP45;
                nameMarginParams.leftMargin = DP10;
                avatarMarginParams.leftMargin = DP10;
                avatarMarginParams.topMargin = DP15;
                mHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                mHolder.mTvCalorie2.setText(mModule.getKc());
                mHolder.mTvBpm2.setText(mModule.getHeart_rate());
                break;
        }

        if (!mModule.isStatus()) {
            if (mSparseArray.get(position) != null) {
                return;
            } else {
                mModule.setAnimator(ObjectAnimator.ofFloat(mHolder.itemView, "alpha", 0.8f, 0.1f, 0.8f));
            }
            mModule.getAnimator().setDuration(1000);
            mModule.getAnimator().setRepeatMode(ValueAnimator.INFINITE);
            mModule.getAnimator().setRepeatCount(Integer.MAX_VALUE);
            mSparseArray.put(position, mModule);
            mModule.getAnimator().addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (mSparseArray.get(position) != null) {
                        mSparseArray.get(position).setAlpha((Float) animation.getAnimatedValue());
                    }
                }
            });
            mModule.getAnimator().start();
        } else {
            if (mSparseArray.get(position) != null) {
                mSparseArray.get(position).getAnimator().cancel();
                mSparseArray.remove(position);
            }
            mHolder.itemView.setAlpha(1);
        }
    }


    @Override
    public int getItemCount() {
        return CollectionsUtil.size(mModules);
    }

    @Override
    public void offset(int position, float offset1, float offset2, float offset3) {
        mCurrentOffset = HomeActivity.sOffsetCache.get(position);
        mCurrentOffset.setOffset1(offset1);
        mCurrentOffset.setOffset2(offset2);
        mCurrentOffset.setOffset3(offset3);
    }

    class HomeHolder extends BaseViewHolder {

        @BindView(R.id.item1_avatar)
        ImageView mIvAvatar;
        @BindView(R.id.item1_name)
        TextView mTvName;
        @BindView(R.id.item1_wave)
        WaveView mWaveView;
        @BindView(R.id.item1_percent)
        TextView mTvPercent;
        @BindView(R.id.item1_calorie)
        TextView mTvCalorie;
        @BindView(R.id.item1_bpm)
        TextView mTvBpm;
        @BindView(R.id.item1_root)
        LinearLayout mLlRoot;

        @BindView(R.id.item1_top_layout)
        ConstraintLayout mClTop;


        @BindView(R.id.item2_temp_layout)
        ConstraintLayout mClMiddle;

        @BindView(R.id.item3_temp_layout)
        ConstraintLayout mClBottom;
        @BindView(R.id.item2_calorie)
        TextView mTvCalorie2;
        @BindView(R.id.item2_bpm)
        TextView mTvBpm2;

        HomeHolder(View itemView) {
            super(itemView);
        }
    }


}
