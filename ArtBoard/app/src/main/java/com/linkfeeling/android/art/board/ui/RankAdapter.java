package com.linkfeeling.android.art.board.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.link.feeling.framework.base.BaseViewHolder;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019/6/10  14:54
 * chenpan pan.chen@linkfeeling.cn
 */
public final class RankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<HomeRemoteModule> mModules;

    private Animation animation;
    private Animation animation1;

    public RankAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setModules(List<HomeRemoteModule> mModules) {
        this.mModules = mModules;
        notifyDataSetChanged();
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

    }

    @Override
    public int getItemCount() {
        return CollectionsUtil.size(mModules);
    }


    public class RankHolder extends BaseViewHolder {

        RankHolder(View itemView) {
            super(itemView);
        }
    }
}
