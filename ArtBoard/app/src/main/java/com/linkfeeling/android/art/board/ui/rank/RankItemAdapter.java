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
import com.link.feeling.framework.StringConstants;
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


    private Context mContext;
    private int mIndex;
    private int mPage = 0;

    private int mColor1 = Color.parseColor("#FF43436F");
    private int mColor2 = Color.parseColor("#FF61618B");

    private List<RankRemoteItem> mItems;

    private CircleTransform mCircleTransform;

    RankItemAdapter(Context mContext, int mIndex) {
        this.mContext = mContext;
        this.mIndex = mIndex;
        mCircleTransform = new CircleTransform();
    }

    void setItems(List<RankRemoteItem> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    void setPage(int mPage) {
        this.mPage = mPage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.rank_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RankRemoteItem item = mItems.get(position % CollectionsUtil.size(mItems));
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.itemView.setBackgroundColor(position % 2 == 0 ? mColor2 : mColor1);

        itemHolder.mLogo.setVisibility((position < 2 && mPage == 0) ? View.VISIBLE : View.GONE);
        itemHolder.mTvNum.setVisibility(position < 2 && mPage == 0 ? View.GONE : View.VISIBLE);
        itemHolder.mLogo.setImageResource(ImageConstants.matchRankImage(position % CollectionsUtil.size(mItems) + 2));

        itemHolder.mTvNum.setText(String.valueOf(mPage * 5 + position + 2));
        LinkImageLoader.INSTANCE.load(item.getHead_icon(), itemHolder.mAvatar, mCircleTransform);
        itemHolder.mTvHolder.setText(StringUtils.isEmpty(item.getUid()) ? "" : StringConstants.matchHolder(mIndex));
        itemHolder.mTvName.setText(item.getUser_name());
        itemHolder.mTvValue.setText(item.getFormatValue(mIndex));
    }

    @Override
    public int getItemCount() {
        return CollectionsUtil.size(mItems);
    }

    class ItemHolder extends BaseViewHolder {
        @BindView(R.id.item_rank_iv)
        ImageView mLogo;
        @BindView(R.id.item_rank_tv)
        TextView mTvNum;
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
}
