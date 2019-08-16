package com.linkfeeling.android.art.board.ui.home;

import android.animation.ObjectAnimator;
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
import com.linkfeeling.android.art.board.data.bean.home.HomeRemoteModule;
import com.linkfeeling.android.art.board.data.bean.home.OffsetModule;
import com.linkfeeling.android.art.board.widget.WaveView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created on 2019/5/14  10:59
 * chenpan pan.chen@linkfeeling.cn
 */
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

//        if (!mModule.isOnline()) {
//            mSparseArray.put(position, mModule);
//        } else {
//            mHolder.itemView.setAlpha(1);
//        }
        mHolder.mWaveView.initValueManager(position, HomeAdapter.this, mInflateOffset.getOffset1(), mInflateOffset.getOffset2(), mInflateOffset.getOffset3(), ColorConstants.loadColors(mModule.getPercent()), mModule.isOnline());


        LinkImageLoader.INSTANCE.load(mModule.getHead_icon(), mHolder.mIvAvatar, mCircleTransform);
        mHolder.mTvName.setText(mModule.getUser_name());
        mHolder.mTvPercent.setText(mModule.getPercentStr());
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
                params.height = (int) (mRecyclerViewHeight * 0.98);
                params.width = (int) (mRecyclerViewWidth * 0.98);
                nameMarginParams.leftMargin = DP30;
                avatarMarginParams.leftMargin = DP30;
                avatarMarginParams.topMargin = DP30;
                mHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                mHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 66);
                mHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56);
                mHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56);
                break;
            case 2:
                mHolder.mTvCalorie.setText(mModule.getKc());
                mHolder.mTvBpm.setText(mModule.getHeart_rate());
                ViewUtils.setVisible(mHolder.mClMiddle);
                params.height = (int) (mRecyclerViewHeight * 0.98);
                params.width = (int) (mRecyclerViewWidth * 0.52);
                nameMarginParams.leftMargin = DP20;
                avatarMarginParams.leftMargin = DP20;
                avatarMarginParams.topMargin = DP30;
                mHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                mHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                mHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                break;
            case 3:
            case 4:
                mHolder.mTvCalorie.setText(mModule.getKc());
                mHolder.mTvBpm.setText(mModule.getHeart_rate());
                ViewUtils.setVisible(mHolder.mClMiddle);
                marginParams.topMargin = DP10;
                rootParams.height = WRAP_CONTENT;
                params.height = (int) (mRecyclerViewHeight * 0.55);
                params.width = (int) (mRecyclerViewWidth * 0.52);
                ivParams.width = DP40;
                ivParams.height = DP40;
                nameMarginParams.leftMargin = DP20;
                avatarMarginParams.leftMargin = DP20;
                avatarMarginParams.topMargin = DP15;
                mHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                mHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                mHolder.mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                mHolder.mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                break;
            case 5:
            case 6:
            default:
                ViewUtils.setVisible(mHolder.mClBottom);
                marginParams.topMargin = DP10;
                rootParams.height = WRAP_CONTENT;
                params.height = (int) (mRecyclerViewHeight * 0.55);
                params.width = (int) (mRecyclerViewWidth * 0.25);
                marginParams.topMargin = DP10;
                ivParams.width = DP30;
                ivParams.height = DP30;
                nameMarginParams.leftMargin = DP10;
                avatarMarginParams.leftMargin = DP10;
                avatarMarginParams.topMargin = DP15;
                mHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                mHolder.mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                mHolder.mTvCalorie2.setText(mModule.getKc());
                mHolder.mTvBpm2.setText(mModule.getHeart_rate());
                break;
        }


        ObjectAnimator animator ;
        if (!mModule.isOnline()) {
            if (mSparseArray.get(position) != null) {
                return;
            } else {
                animator = ObjectAnimator.ofFloat(mHolder.itemView, "alpha", 0.8f, 0.1f, 0.8f);
            }
            animator.setDuration(1000);
            animator.setRepeatCount(-1);
            mModules.get(position).setAnimator(animator);
            mSparseArray.put(position, mModules.get(position));
            animator.start();
            animator.addUpdateListener(animation -> {
                if (mSparseArray.get(position) != null) {
                    mSparseArray.get(position).setAlpha((Float) animation.getAnimatedValue());
                }
            });
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
