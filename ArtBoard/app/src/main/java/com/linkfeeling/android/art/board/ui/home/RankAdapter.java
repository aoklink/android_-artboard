package com.linkfeeling.android.art.board.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.link.feeling.framework.base.BaseViewHolder;
import com.link.feeling.framework.component.image.LinkImageLoader;
import com.link.feeling.framework.component.image.transformation.CircleTransform;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DisplayUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ImageConstants;
import com.linkfeeling.android.art.board.data.bean.home.HomeRemoteModule;
import com.linkfeeling.android.art.board.widget.Base64Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created on 2019/6/10  14:54
 * chenpan pan.chen@linkfeeling.cn
 */
public final class RankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<HomeRemoteModule> mModules;

    private CircleTransform mCircleTransform;

    private int mCurrentPage = 0;

    private int mItemHeight;

    RankAdapter(Context mContext) {
        this.mContext = mContext;
        mCircleTransform = new CircleTransform();
        mItemHeight = (int) ((DisplayUtils.getScreenHeight()-DisplayUtils.dp2px(80))/5);
    }

    void setModules(List<HomeRemoteModule> mModules) {
        this.mModules = mModules;
        notifyDataSetChanged();
    }

    public void setCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RankHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RankHolder rankHolder = (RankHolder) holder;
        HomeRemoteModule module = mModules.get(position);

        LinkImageLoader.INSTANCE.load(ImageConstants.matchRankImage(mCurrentPage * 5 + position + 1), rankHolder.mIvTag);
        LinkImageLoader.INSTANCE.load(module.getHead_icon(), rankHolder.mIvAvatar, mCircleTransform);

        rankHolder.mTvName.setText(Base64Utils.URLDecoder(module.getUser_name()));
        rankHolder.mTvCalorie.setText(module.getKc());
    }

    @Override
    public int getItemCount() {
        return CollectionsUtil.size(mModules);
    }


    class RankHolder extends BaseViewHolder {

        @BindView(R.id.rank_item_tag)
        ImageView mIvTag;
        @BindView(R.id.rank_item_avatar)
        ImageView mIvAvatar;

        @BindView(R.id.rank_item_name)
        TextView mTvName;
        @BindView(R.id.rank_item_calorie)
        TextView mTvCalorie;

        RankHolder(View itemView) {
            super(itemView);
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.height = mItemHeight;
        }
    }
}
