package com.linkfeeling.android.art.board.data.bean;

import android.animation.Animator;

import com.alibaba.fastjson.annotation.JSONField;
import com.linkfeeling.android.art.board.widget.Base64Utils;

import java.util.Objects;

/**
 * Created on 2019/5/14  11:38
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class HomeRemoteModule {


    /**
     * kc : 1240
     * heart_rate : 130
     * uid : xxxxx
     * user_name : jack
     * head_icon : https://img.linkfeeling.cn/img/oo.png
     * result : 1
     */

    @JSONField(name = "calorie")
    private String calorie;
    @JSONField(name = "uid")
    private String uid;
    @JSONField(name = "heart_rate")
    private String heart_rate;
    @JSONField(name = "user_name")
    private String user_name;
    @JSONField(name = "head_icon")
    private String head_icon;
    @JSONField(name = "ratio")
    private int ratio;
    @JSONField(name = "status")
    private boolean status;
    @JSONField(name = "ratio_warn")
    private boolean ratio_warn;

    private int type;

    private float alpha = 0.8f;
    private Animator animator;
    private long millis;
    private Animator animator1;
    private Animator animator2;

    public Animator getAnimator2() {
        return animator2;
    }

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }
    public void setAnimator2(Animator animator2) {
        this.animator2 = animator2;
    }

    public Animator getAnimator1() {
        return animator1;
    }

    public void setAnimator1(Animator animator1) {
        this.animator1 = animator1;
    }

    public boolean isRatio_warn() {
        return ratio_warn;
    }

    public void setRatio_warn(boolean ratio_warn) {
        this.ratio_warn = ratio_warn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public String getCalorie() {
        return calorie == null ? "" : calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getKc() {
        return calorie;
    }

    public void setKc(String kc) {
        this.calorie = kc;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getUser_name() {
        return Base64Utils.URLDecoder(user_name);
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

    public int getPercent() {
        return ratio;
    }

    public String getPercentStr() {
        return ratio + "%";
    }

    public void setPercent(int percent) {
        this.ratio = percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeRemoteModule module = (HomeRemoteModule) o;
        return ratio == module.ratio &&
                status == module.status &&
                Objects.equals(calorie, module.calorie) &&
                Objects.equals(uid, module.uid) &&
                Objects.equals(heart_rate, module.heart_rate) &&
                Objects.equals(user_name, module.user_name) &&
                Objects.equals(head_icon, module.head_icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calorie, uid, heart_rate, user_name, head_icon, ratio, status);
    }
}
