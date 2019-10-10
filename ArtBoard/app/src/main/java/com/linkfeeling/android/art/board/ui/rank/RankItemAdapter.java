package com.linkfeeling.android.art.board.ui.rank;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.link.feeling.framework.base.BaseViewHolder;
import com.link.feeling.framework.component.image.LinkImageLoader;
import com.link.feeling.framework.component.image.transformation.CircleTransform;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.StringUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ImageConstants;
import com.linkfeeling.android.art.board.constants.StringConstants;
import com.linkfeeling.android.art.board.data.bean.rank.RankRemoteItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created on 2019/9/26  14:41
 * chenpan pan.chen@linkfeeling.cn
 */
public final class RankItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TOP_TYPE = 100;
    public static final int NORMAL_TYPE = 101;


    private Context mContext;
    private int mIndex;

    private int mColor1 = Color.parseColor("#FF43436F");
    private int mColor2 = Color.parseColor("#FF61618B");

    private List<RankRemoteItem> mItems;

    private CircleTransform mCircleTransform;

    RankItemAdapter(Context mContext, int mIndex) {
        this.mContext = mContext;
        this.mIndex = mIndex;
        mCircleTransform = new CircleTransform();
    }

    public void setItems(List<RankRemoteItem> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position % CollectionsUtil.size(mItems) == 0 ? TOP_TYPE : NORMAL_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewType == TOP_TYPE ? new Item1Holder(LayoutInflater.from(mContext).inflate(R.layout.rank_item1, parent, false)) : new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.rank_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RankRemoteItem item = mItems.get(position % CollectionsUtil.size(mItems));
        if (getItemViewType(position) == TOP_TYPE) {
            Item1Holder itemHolder = (Item1Holder) holder;
            itemHolder.itemView.setBackgroundColor(position % 2 == 0 ? mColor1 : mColor2);
            itemHolder.mLogo.setImageResource(ImageConstants.matchRankImage(position % CollectionsUtil.size(mItems) + 1));
            LinkImageLoader.INSTANCE.load(item.getHead_icon(), itemHolder.mAvatar, mCircleTransform);
            itemHolder.mTvHolder.setText(StringUtils.isEmpty(item.getUid()) ? "" : StringConstants.matchHolder(mIndex));
            itemHolder.mTvName.setText(item.getUser_name());
            itemHolder.mTvValue.setText(mIndex == 3 ? item.formatDuration() : item.getValue());
        } else {
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.itemView.setBackgroundColor(position % 2 == 0 ? mColor1 : mColor2);
            itemHolder.mLogo.setImageResource(ImageConstants.matchRankImage(position % CollectionsUtil.size(mItems) + 1));
            LinkImageLoader.INSTANCE.load(item.getHead_icon(), itemHolder.mAvatar, mCircleTransform);
            itemHolder.mTvHolder.setText(StringUtils.isEmpty(item.getUid()) ? "" : StringConstants.matchHolder(mIndex));
            itemHolder.mTvName.setText(item.getUser_name());
            itemHolder.mTvValue.setText(mIndex == 3 ? item.formatDuration() : item.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return isGo(mItems) ? CollectionsUtil.size(mItems) : Integer.MAX_VALUE;
    }

    private boolean isGo(List<RankRemoteItem> items) {
        if (CollectionsUtil.isEmpty(items)) {
            return true;
        }
        int index = 0;
        for (RankRemoteItem item : items) {
            if (StringUtils.isNotEmpty(item.getUid())) {
                index++;
            }
        }
        return index <= 5;
    }

    class ItemHolder extends BaseViewHolder {
        @BindView(R.id.item_rank_iv)
        ImageView mLogo;
        @BindView(R.id.item_rank_avatar)
        ImageView mAvatar;
        @BindView(R.id.item_rank_holder)
        TextView mTvHolder;
        @BindView(R.id.item_rank_name)
        TextView mTvName;
        @BindView(R.id.item_rank_value)
        TextView mTvValue;

        ItemHolder(View itemView) {
            super(itemView);
        }
    }

    class Item1Holder extends BaseViewHolder {
        @BindView(R.id.item_rank_iv1)
        ImageView mLogo;
        @BindView(R.id.item_rank_avatar1)
        ImageView mAvatar;
        @BindView(R.id.item_rank_holder1)
        TextView mTvHolder;
        @BindView(R.id.item_rank_name1)
        TextView mTvName;
        @BindView(R.id.item_rank_value1)
        TextView mTvValue;

        Item1Holder(View itemView) {
            super(itemView);
        }
    }
}
