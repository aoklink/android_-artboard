package com.linkfeeling.android.art.board.ui.list;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.link.feeling.framework.base.BaseViewHolder;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.ui.ViewUtils;
import com.linkfeeling.android.art.board.R;
import com.linkfeeling.android.art.board.constants.ColorConstants;
import com.linkfeeling.android.art.board.data.bean.list.GymListModule;
import com.linkfeeling.android.art.board.ui.home.HomeActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019/8/16  10:46
 * chenpan pan.chen@linkfeeling.cn
 */
public final class GymListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    private List<GymListModule> mModules;

    public GymListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setModules(List<GymListModule> mModules) {
        this.mModules = mModules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GymListHolder(LayoutInflater.from(mContext).inflate(R.layout.gym_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GymListModule module = mModules.get(position);
        GymListHolder gymHolder = (GymListHolder) holder;
        ((Button) gymHolder.itemView).setText(module.getGym());

        gymHolder.itemView.setBackgroundColor(ColorConstants.loadingListColor(position));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gymHolder.itemView.setElevation(15f);
        }
        gymHolder.itemView.setTag(module);
    }

    @Override
    public int getItemCount() {
        return CollectionsUtil.size(mModules);
    }

    @Override
    public void onClick(View v) {
        if (ViewUtils.isQuickClick()) {
            return;
        }
        HomeActivity.launch(mContext, (GymListModule) v.getTag());
    }


    class GymListHolder extends BaseViewHolder {

        GymListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(GymListAdapter.this);
        }
    }
}
