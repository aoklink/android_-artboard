package com.linkfeeling.android.art.board.data.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created on 2019/5/14  19:02
 * chenpan pan.chen@linkfeeling.cn
 */
public final class HomeRemoteBean {

    @JSONField(name = "gym_name")
    private String gym_name;
    @JSONField(name = "logo_url")
    private String logo_url;
    @JSONField(name = "gym_data")
    private List<HomeRemoteModule> gym_data;


    public String getGym_name() {
        return gym_name == null ? "" : gym_name;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
    }

    public String getLogo_url() {
        return logo_url == null ? "" : logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public List<HomeRemoteModule> getGym_data() {
        return gym_data;
    }

    public void setGym_data(List<HomeRemoteModule> gym_data) {
        this.gym_data = gym_data;
    }
}
