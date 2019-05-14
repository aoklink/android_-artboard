package com.linkfeeling.android.art.board.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.link.feeling.framework.base.BaseViewHolder;
import com.link.feeling.framework.component.image.LinkImageLoader;
import com.link.feeling.framework.component.image.transformation.CircleTransform;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;
import com.linkfeeling.android.art.board.widget.WaveView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created on 2019/5/14  10:59
 * chenpan pan.chen@linkfeeling.cn
 */
public final class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<HomeRemoteModule> mModules;

    private int mRecyclerViewHeight;
    private int mRecyclerViewWidth;

    private int DP10 = (int) DisplayUtils.dp2px(10);
    private int DP30 = (int) DisplayUtils.dp2px(30);
    private int DP45 = (int) DisplayUtils.dp2px(45);
    private int DP50 = (int) DisplayUtils.dp2px(50);

    HomeAdapter(Context mContext) {
        this.mContext = mContext;
        mModules = new ArrayList<>();
    }

    void setModules(List<HomeRemoteModule> mModules) {
        this.mModules.clear();
        this.mModules.addAll(mModules);
        notifyDataSetChanged();
    }

    public void setRecyclerViewHeight(float mRecyclerViewHeight) {
        this.mRecyclerViewHeight = (int) mRecyclerViewHeight;
    }

    public void setRecyclerViewWidth(float mRecyclerViewWidth) {
        this.mRecyclerViewWidth = (int) mRecyclerViewWidth;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item1, parent, false);
        return new HomeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeRemoteModule module = mModules.get(position);
        HomeHolder homeHolder = (HomeHolder)holder;
        LinkImageLoader.INSTANCE.load(module.getHead_icon() , homeHolder.mIvAvatar , new CircleTransform());

        ViewGroup.LayoutParams params = homeHolder.mLlRoot.getLayoutParams();
        ViewGroup.LayoutParams rootParams = homeHolder.itemView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) homeHolder.itemView.getLayoutParams();
        ViewGroup.LayoutParams ivParams = homeHolder.mIvAvatar.getLayoutParams();

        switch (CollectionsUtil.size(mModules)) {
            case 1:
                params.height = (int) (mRecyclerViewHeight*0.9);
                params.width = (int) (mRecyclerViewWidth*0.9);
                homeHolder.mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                homeHolder. mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
                homeHolder.  mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
                homeHolder. mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
                break;
            case 2:
                params.height = (int) (mRecyclerViewHeight * 0.75);
                params.width = (int) (mRecyclerViewWidth * 0.45);
                homeHolder. mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                homeHolder. mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 42);
                homeHolder.  mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                homeHolder.  mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                break;
            case 3:
            case 4:
                marginParams.topMargin = DP30;
                rootParams.height = WRAP_CONTENT;
                params.height = (int) (mRecyclerViewHeight * 0.4);
                params.width = (int) (mRecyclerViewWidth * 0.47);
                ivParams.width = DP45;
                ivParams.height = DP45;
                homeHolder.  mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                homeHolder.  mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 42);
                homeHolder.   mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                homeHolder.  mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                break;
            case 5:
            case 6:
            default:
                marginParams.topMargin = DP10;
                rootParams.height = WRAP_CONTENT;
                params.height = (int) (mRecyclerViewHeight * 0.4);
                params.width = (int) (mRecyclerViewWidth * 0.23);
                marginParams.topMargin = DP30;
                ivParams.width = DP30;
                ivParams.height = DP30;
                homeHolder.  mTvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                homeHolder.  mTvPercent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                homeHolder.   mTvCalorie.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                homeHolder.   mTvBpm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return CollectionsUtil.size(mModules);
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

        HomeHolder(View itemView) {
            super(itemView);
        }
    }
}
