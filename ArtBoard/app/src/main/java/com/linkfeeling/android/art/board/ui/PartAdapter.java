package com.linkfeeling.android.art.board.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.link.feeling.framework.base.BaseViewHolder;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.data.bean.HomePartModule;
import com.linkfeeling.android.art.board.widget.BackgroundProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created on 2019/5/14  19:53
 * chenpan pan.chen@linkfeeling.cn
 */
public final class PartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<HomePartModule> mPartModules;

    public PartAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setPartModules(List<HomePartModule> mPartModules) {
        this.mPartModules = mPartModules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PartHolder(LayoutInflater.from(mContext).inflate(R.layout.user_sport_summary_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PartHolder partHolder = (PartHolder) holder;
        HomePartModule module = mPartModules.get(position);

        partHolder.nameTv.setText(module.getName());
        partHolder.valueTv.setText(module.getValue());
        partHolder.labelView.setBackground(BackgroundProvider.get(BackgroundProvider.AT_USER_SPORT_SUMMARY, module.getName(), position));

    }

    @Override
    public int getItemCount() {
        return CollectionsUtil.size(mPartModules);
    }


     class PartHolder extends BaseViewHolder {

        @BindView(R.id.sport_summary_item_name_tv)
        TextView nameTv;
        @BindView(R.id.sport_summary_item_value_tv)
         TextView valueTv;
        @BindView(R.id.sport_summary_item_label)
         View labelView;

         PartHolder(View itemView) {
            super(itemView);
        }
    }
}
