package com.linkfeeling.android.art.board.data.bean.rank;

import java.util.Map;

/**
 * Created on 2019/9/27  10:37
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class RankUpdateModule {


    /**
     * user_name : _%E6%94%80
     * head_icon : https://img.linkfeeling.cn/user/head_icon/bcc50250653686af9ade3f692d527feb.gif
     * type : 203
     * uid : bcc50250653686af9ade3f692d527feb
     * data : {"0":292,"2":3817}
     */

    private String user_name;
    private String head_icon;
    private int type;
    private String uid;
    private Map<Integer ,InsertItemModule> data;

    public String getUser_name() {
        return user_name == null ? "" : user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHead_icon() {
        return head_icon == null ? "" : head_icon;
    }

    public void setHead_icon(String head_icon) {
        this.head_icon = head_icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Map<Integer, InsertItemModule> getData() {
        return data;
    }

    public void setData(Map<Integer, InsertItemModule> data) {
        this.data = data;
    }
}
