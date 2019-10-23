package com.linkfeeling.android.art.board.data.bean;

import java.util.List;

/**
 * Created on 2019/10/11  10:52
 * chenpan pan.chen@linkfeeling.cn
 */
public final class HomeModule {
    private int type;
    private List<HomeRemoteModule> data;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<HomeRemoteModule> getData() {
        return data;
    }

    public void setData(List<HomeRemoteModule> data) {
        this.data = data;
    }
}
