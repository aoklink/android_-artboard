package com.linkfeeling.android.art.board.data.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created on 2019/5/14  19:02
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class HomeRemoteBean {

    @JSONField(name = "flag")
    private boolean flag;
   @JSONField(name = "total_calorie")
    private String total_calorie;
    @JSONField(name = "gym_data")
    private List<HomeRemoteModule> gym_data;

    public HomeRemoteBean() {
    }

    public String getTotal_calorie() {
        return total_calorie == null ? "" : total_calorie;
    }

    public void setTotal_calorie(String total_calorie) {
        this.total_calorie = total_calorie;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<HomeRemoteModule> getGym_data() {
        return gym_data;
    }

    public void setGym_data(List<HomeRemoteModule> gym_data) {
        this.gym_data = gym_data;
    }
}
