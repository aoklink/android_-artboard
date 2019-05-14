package com.link.feeling.framework.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created on 2019/1/14  16:32
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public class BaseViewHolder extends RecyclerView.ViewHolder {

    protected final Context mContext;
    protected final LayoutInflater mLayoutInflater;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
    }
}

