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
    @JSONField(name = "heart_rate")
    private String heart_rate;
    @JSONField(name = "user_name")
    private String user_name;
    @JSONField(name = "head_icon")
    private String head_icon;
    @JSONField(name = "ratio")
    private int ratio;
    @JSONField(name = "online")
    private boolean online;

    private float alpha = 0.8f;
    private Animator animator;


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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
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
                online == module.online &&
                Objects.equals(calorie, module.calorie) &&
                Objects.equals(heart_rate, module.heart_rate) &&
                Objects.equals(user_name, module.user_name) &&
                Objects.equals(head_icon, module.head_icon);
    }

    @Override
    public int hashCode() {

        return Objects.hash(calorie, heart_rate, user_name, head_icon, ratio, online);
    }
}
