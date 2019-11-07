package com.linkfeeling.android.art.board.data.bean.rank;

/**
 * Created on 2019/11/7  11:56
 * chenpan pan.chen@linkfeeling.cn
 */
public final class InsertItemModule {
    private String value;
    private int index;

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
