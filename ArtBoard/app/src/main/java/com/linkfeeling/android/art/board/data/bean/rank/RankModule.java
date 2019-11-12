package com.linkfeeling.android.art.board.data.bean.rank;

import java.util.List;

/**
 * Created on 2019/11/6  13:52
 * chenpan pan.chen@linkfeeling.cn
 */
public final class RankModule {

    private int type;

    private List<RankRemoteModule> data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<RankRemoteModule> getData() {
        return data;
    }

    public void setData(List<RankRemoteModule> data) {
        this.data = data;
    }

}
