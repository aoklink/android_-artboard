package com.linkfeeling.android.art.board.data.bean.rank;

/**
 * Created on 2019/9/27  10:23
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class RankRemoteItem {

    /**
     * user_name :
     * head_icon :
     * uid :
     * value : 123
     */

    private String user_name;
    private String head_icon;
    private String uid;
    private String value;

    public RankRemoteItem() {
    }

    public RankRemoteItem(String user_name, String head_icon, String uid, String value) {
        this.user_name = user_name;
        this.head_icon = head_icon;
        this.uid = uid;
        this.value = value;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHead_icon() {
        return head_icon;
    }

    public void setHead_icon(String head_icon) {
        this.head_icon = head_icon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
