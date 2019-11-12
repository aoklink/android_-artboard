package com.linkfeeling.android.art.board.data.bean;

/**
 * Created on 2019/10/11  11:17
 * chenpan pan.chen@linkfeeling.cn
 */
public final class HeartRemoteModule {

    /**
     * type : 201
     * uid : xxxx
     * heart_rate : 87
     * calorie : 100
     */

    private int type;
    private String uid;
    private String heart_rate;
    private String calorie;
    private int ratio;
    private boolean ratio_warn;

    public boolean isRatio_warn() {
        return ratio_warn;
    }

    public void setRatio_warn(boolean ratio_warn) {
        this.ratio_warn = ratio_warn;
    }


    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
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

    public String getHeart_rate() {
        return heart_rate == null ? "" : heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getCalorie() {
        return calorie == null ? "" : calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }
}
