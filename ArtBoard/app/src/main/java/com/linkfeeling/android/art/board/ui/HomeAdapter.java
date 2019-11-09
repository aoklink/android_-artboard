package com.linkfeeling.android.art.board.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import java.util.Random;

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

    private int mRecyclerViewHeight;
    private int mRecyclerViewWidth;

    private int DP10 = (int) DisplayUtils.dp2px(10);
    private int DP15 = (int) DisplayUtils.dp2px(15);
    private int DP20 = (int) DisplayUtils.dp2px(20);
    private int DP30 = (int) DisplayUtils.dp2px(30);
    private int DP40 = (int) DisplayUtils.dp2px(40);
    private int DP45 = (int) DisplayUtils.dp2px(45);
    private int DP50 = (int) DisplayUtils.dp2px(50);
    private int DP60 = (int) DisplayUtils.dp2px(60);

    private CircleTransform mCircleTransform;

    int mWarnColor = DisplayUtils.getColor(R.color.color_FFF54646);
    int mNormalColor = DisplayUtils.getColor(R.color.color_FFF5F5F5);

    Drawable mWarnDrawable = DisplayUtils.getDrawable(R.drawable.icon_warn_bpm);
    Drawable mNormalDrawable = DisplayUtils.getDrawable(R.drawable.icon_bpm);

    private int mSize;

    private boolean mIsAnimator;

    HomeAdapter(Context mContext) {
        this.mContext = mContext;
        this.mModules = new ArrayList<>();
        mCircleTransform = new CircleTransform();
    }

    void setModules(List<HomeRemoteModule> mModules) {
        this.mModules.clear();
        this.mModules.addAll(mModules);
        notifyDataSetChanged();
    }

    public void setIsAnimator(boolean mIsAnimator) {
        this.mIsAnimator = mIsAnimator;
    }

    //    void notifyRangeChanged(List<HomeRemoteModule> mModules) {
//        int size = CollectionsUtil.size(this.mModules);
//        this.mModules.clear();
//        this.mModules.addAll(mModules);
//        notifyItemRangeChanged(0 ,size>CollectionsUtil.size(mModules)?size:CollectionsUtil.size(mModules));
////        notifyItemRangeChanged(0, CollectionsUtil.size(this.mModules));
//    }

    void setRecyclerViewHeight(float mRecyclerViewHeight) {
        this.mRecyclerViewHeight = (int) mRecyclerViewHeight;
    }

    void setRecyclerViewWidth(float mRecyclerViewWidth) {
        this.mRecyclerViewWidth = (int) mRecyclerViewWidth;
    }

    public void setSize(int mSize) {
        this.mSize = mSize;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item1, parent, false);
        return new HomeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        HomeRemoteModule module = mModules.get(position);
        HomeHolder homeHolder = (HomeHolder) holder;
        homeHolder.itemView.setTag(position);
//
        Random random = new Random();
        module.setPercent(random.nextInt(100));
        module.setRatio_warn(module.getPercent() > 50);

        mInflateOffset = HomeActivity.sOffsetCache.get(position);

        homeHolder.mWaveView.initValueManager(position, HomeAdapter.this, mInflateOffset.getOffset1(), mInflateOffset.getOffset2(), mInflateOffset.getOffset3(), ColorConstants.loadColors(module.getPercent()), module.isStatus());

        LinkImageLoader.INSTANCE.load(module.getHead_icon(), homeHolder.mIvAvatar, mCircleTransform);

        homeHolder.mTvName.setText(module.getUser_name());

        if (!homeHolder.mTvPercent.getText().equals(module.getPercentStr())) {
            homeHolder.mTvPercent.setText(module.getPercentStr());
        }

        homeHolder.mClTop.setBackgroundColor(ColorConstants.loadColor(module.getPercent()));

        ViewGroup.LayoutParams params = homeHolder.mLlRoot.getLayoutParams();
        ViewGroup.LayoutParams rootParams = homeHolder.itemView.getLayoutParams();
        ViewGroup.LayoutParams ivParams = homeHolder.mIvAvatar.getLayoutParams();

        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) homeHolder.itemView.getLayoutParams();
        ViewGroup.MarginLayoutParams nameMarginParams = (ViewGroup.MarginLayoutParams) homeHolder.mTvName.getLayoutParams();
        ViewGroup.MarginLayoutParams avatarMarginParams = (ViewGroup.MarginLayoutParams) homeHolder.mIvAvatar.getLayoutParams();

        ViewUtils.setGone(homeHolder.mClMiddle);
        ViewUtils.setGone(homeHolder.mClBottom);

        switch (mSize) {
            case 1:
                homeHolder.mTvCalorie.setText(module.getKc());
                homeHolder.mTvBpm.setText(module.getHeart_rate());
                ViewUtils.setVisible(homeHolder.mClMiddle);
                params.height = (int) (mRecyclerViewHeight * 0.9);
                params.width = (int) (mRecyclerViewWidth * 0.9);
                rootParams.height = MATCH_PARENT;
                nameMarginParams.leftMargin = DP30;
                avatarMarginParams.leftMargin = DP30;
                avatarMarginParams.topMargin = DP30;
                ivParams.height = DP60;
                ivParams.width = DP60;
                homeHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
                homeHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 88);
                homeHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 66);
                homeHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 66);

                if (!homeHolder.mTvBpm.getText().equals(module.getHeart_rate())) {
                    homeHolder.mTvBpm.setText(module.getHeart_rate());
                }
                if (!homeHolder.mTvCalorie.getText().equals(module.getKc())) {
                    homeHolder.mTvCalorie.setText(module.getKc());
                }
                break;
            case 2:
                homeHolder.mTvCalorie.setText(module.getKc());
                homeHolder.mTvBpm.setText(module.getHeart_rate());
                ViewUtils.setVisible(homeHolder.mClMiddle);
                rootParams.height = MATCH_PARENT;
                params.height = (int) (mRecyclerViewHeight * 0.90);
                params.width = (int) (mRecyclerViewWidth * 0.45);
                nameMarginParams.leftMargin = DP20;
                avatarMarginParams.leftMargin = DP20;
                avatarMarginParams.topMargin = DP30;
                ivParams.height = DP60;
                ivParams.width = DP60;
                homeHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                homeHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 66);
                homeHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                homeHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                if (!homeHolder.mTvBpm.getText().equals(module.getHeart_rate())) {
                    homeHolder.mTvBpm.setText(module.getHeart_rate());
                }
                if (!homeHolder.mTvCalorie.getText().equals(module.getKc())) {
                    homeHolder.mTvCalorie.setText(module.getKc());
                }
                break;
            case 3:
            case 4:
                homeHolder.mTvCalorie.setText(module.getKc());
                homeHolder.mTvBpm.setText(module.getHeart_rate());
                ViewUtils.setVisible(homeHolder.mClMiddle);
                marginParams.topMargin = DP40;
                rootParams.height = WRAP_CONTENT;
                params.height = (int) (mRecyclerViewHeight * 0.45);
                params.width = (int) (mRecyclerViewWidth * 0.47);
                ivParams.width = DP60;
                ivParams.height = DP60;
                nameMarginParams.leftMargin = DP20;
                avatarMarginParams.leftMargin = DP20;
                avatarMarginParams.topMargin = DP15;
                homeHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                homeHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 58);
                homeHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                homeHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                break;
            case 5:
            case 6:
            default:
                ViewUtils.setVisible(homeHolder.mClBottom);
                marginParams.topMargin = DP40;
                rootParams.height = WRAP_CONTENT;
                params.height = (int) (mRecyclerViewHeight * 0.45);
                params.width = (int) (mRecyclerViewWidth * 0.23);
                ivParams.width = DP50;
                ivParams.height = DP50;
                nameMarginParams.leftMargin = DP10;
                avatarMarginParams.leftMargin = DP10;
                avatarMarginParams.topMargin = DP15;
                homeHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                homeHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56);
                homeHolder.mTvCalorie2.setText(module.getKc());
                homeHolder.mTvBpm2.setText(module.getHeart_rate());
                break;
        }

        homeHolder.mIvWarn.setVisibility(module.isRatio_warn() ? View.VISIBLE : View.GONE);
        homeHolder.mTvBpm.setTextColor(module.isRatio_warn() ? mWarnColor : mNormalColor);
        homeHolder.mTvBpmHolder.setTextColor(module.isRatio_warn() ? mWarnColor : mNormalColor);
        ViewUtils.setDrawableLeft(homeHolder.mTvBpm, module.isRatio_warn() ? R.drawable.icon_warn_bpm : R.drawable.icon_bpm);

        homeHolder.mTvBpm2.setTextColor(module.isRatio_warn() ? mWarnColor : mNormalColor);
        homeHolder.mTvBpm2Holder.setTextColor(module.isRatio_warn() ? mWarnColor : mNormalColor);
        ViewUtils.setDrawableLeft(homeHolder.mTvBpm2, module.isRatio_warn() ? R.drawable.icon_warn_bpm : R.drawable.icon_bpm);
        homeHolder.mWarnLayout.setVisibility(module.isRatio_warn() ? View.VISIBLE : View.GONE);


        // ********************************离线动画******************************
        // ********************************离线动画******************************
        // ********************************离线动画******************************
        if (!module.isStatus()) {
            module.getAnimatorOnline().cancel();
            module.getAnimatorOffline().setTarget(homeHolder.itemView);
            if (!module.getAnimatorOffline().isStarted()) {
                module.getAnimatorOffline().start();
            }
            return;
        } else {
            module.getAnimatorOffline().cancel();
            module.getAnimatorOnline().setTarget(homeHolder.itemView);
            if (!module.getAnimatorOnline().isStarted()) {
                module.getAnimatorOnline().start();
            }
        }

        // ********************************心率过高动画******************************
        // ********************************心率过高动画******************************
        // ********************************心率过高动画******************************
        if (module.isRatio_warn()) {
            module.getAnimatorBpmNormal().cancel();
            module.getAnimatorWarnNormal().cancel();
            module.getAnimatorBpm().setTarget(CollectionsUtil.size(mModules) < 5 ? homeHolder.mTvBpm : homeHolder.mTvBpm2);
            if (!module.getAnimatorBpm().isStarted()) {
                module.getAnimatorBpm().start();
            }
            module.getAnimatorWarn().setTarget(homeHolder.mWarnLayout);
            if (!module.getAnimatorWarn().isStarted()) {
                module.getAnimatorWarn().start();
            }
        } else {
            module.getAnimatorBpm().cancel();
            module.getAnimatorWarn().cancel();
            module.getAnimatorBpmNormal().setTarget(CollectionsUtil.size(mModules) < 5 ? homeHolder.mTvBpm : homeHolder.mTvBpm2);
            if (!module.getAnimatorBpmNormal().isStarted()) {
                module.getAnimatorBpmNormal().start();
            }
            module.getAnimatorWarnNormal().setTarget(homeHolder.mWarnLayout);
            if (!module.getAnimatorWarnNormal().isStarted()) {
                module.getAnimatorWarnNormal().start();
            }
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

    static class HomeHolder extends BaseViewHolder {

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
        @BindView(R.id.item1_bpm_holder)
        TextView mTvBpmHolder;
        @BindView(R.id.item1_root)
        FrameLayout mLlRoot;

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
        @BindView(R.id.item2_bpm_holder)
        TextView mTvBpm2Holder;

        @BindView(R.id.item1_warn)
        ImageView mIvWarn;
        @BindView(R.id.iv_warn)
        ImageView mWarnLayout;

        HomeHolder(View itemView) {
            super(itemView);

        }
    }


}
