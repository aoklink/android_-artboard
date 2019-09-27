package com.linkfeeling.android.art.board.data.bean.rank;

/**
 * Created on 2019/9/27  10:37
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class RankUpdateModule {

    /**
     * type : 204
     * uid :
     * head_icon :
     * user_name :
     * calorie : 22
     * time : 2223
     * pbj_pace : 2223
     * dc_pace : 2223
     * tyj_pace : 2223
     * total_capacity : 2223
     * single_max_capacity : 2223
     */

    private int type;
    private String uid;
    private String head_icon;
    private String user_name;
    private String calorie;
    private String day;
    private String time;
    private String pbj_pace;
    private String dc_pace;
    private String tyj_pace;
    private String total_capacity;
    private String single_max_capacity;
    private String hdj_max_weight;


    public String getHdj_max_weight() {
        return hdj_max_weight == null ? "" : hdj_max_weight;
    }

    public void setHdj_max_weight(String hdj_max_weight) {
        this.hdj_max_weight = hdj_max_weight;
    }

    public String getDay() {
        return day == null ? "" : day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public String getHead_icon() {
        return head_icon == null ? "" : head_icon;
    }

    public void setHead_icon(String head_icon) {
        this.head_icon = head_icon;
    }

    public String getUser_name() {
        return user_name == null ? "" : user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCalorie() {
        return calorie == null ? "" : calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPbj_pace() {
        return pbj_pace == null ? "" : pbj_pace;
    }

    public void setPbj_pace(String pbj_pace) {
        this.pbj_pace = pbj_pace;
    }

    public String getDc_pace() {
        return dc_pace == null ? "" : dc_pace;
    }

    public void setDc_pace(String dc_pace) {
        this.dc_pace = dc_pace;
    }

    public String getTyj_pace() {
        return tyj_pace == null ? "" : tyj_pace;
    }

    public void setTyj_pace(String tyj_pace) {
        this.tyj_pace = tyj_pace;
    }

    public String getTotal_capacity() {
        return total_capacity == null ? "" : total_capacity;
    }

    public void setTotal_capacity(String total_capacity) {
        this.total_capacity = total_capacity;
    }

    public String getSingle_max_capacity() {
        return single_max_capacity == null ? "" : single_max_capacity;
    }

    public void setSingle_max_capacity(String single_max_capacity) {
        this.single_max_capacity = single_max_capacity;
    }
}
