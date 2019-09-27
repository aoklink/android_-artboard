package com.linkfeeling.android.art.board.data.bean.rank;

import com.link.feeling.framework.utils.data.CollectionsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/9/27  10:27
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class RankRemoteModule {


    /**
     * type : 210
     * calorie : [{"user_name":"","head_icon":"","uid":"","value":122}]
     * day : [{"user_name":"","head_icon":"","uid":"","value":12}]
     * time : [{"user_name":"","head_icon":"","uid":"","value":123}]
     * pbj_pace : [{"user_name":"","head_icon":"","uid":"","value":123}]
     * dc_pace : [{"user_name":"","head_icon":"","uid":"","value":123}]
     * tyj_pace : [{"user_name":"","head_icon":"","uid":"","value":123}]
     * hdj_max_weight : [{"user_name":"","head_icon":"","uid":"","value":12.5}]
     * total_capacity : [{"user_name":"","head_icon":"","uid":"","value":300}]
     * single_max_capacity : [{"user_name":"","head_icon":"","uid":"","value":200}]
     */

    private int type;
    private List<RankRemoteItem> calorie;
    private List<RankRemoteItem> day;
    private List<RankRemoteItem> time;
    private List<RankRemoteItem> pbj_pace;
    private List<RankRemoteItem> dc_pace;
    private List<RankRemoteItem> tyj_pace;
    private List<RankRemoteItem> hdj_max_weight;
    private List<RankRemoteItem> total_capacity;
    private List<RankRemoteItem> single_max_capacity;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<RankRemoteItem> getCalorie() {
        return initRanks(calorie);
    }

    public void setCalorie(List<RankRemoteItem> calorie) {
        this.calorie = calorie;
    }

    public List<RankRemoteItem> getDay() {
        return initRanks(day);
    }

    public void setDay(List<RankRemoteItem> day) {
        this.day = day;
    }

    public List<RankRemoteItem> getTime() {
        return initRanks(time);
    }

    public void setTime(List<RankRemoteItem> time) {
        this.time = time;
    }

    public List<RankRemoteItem> getPbj_pace() {
        return initRanks(pbj_pace);
    }

    public void setPbj_pace(List<RankRemoteItem> pbj_pace) {
        this.pbj_pace = pbj_pace;
    }

    public List<RankRemoteItem> getDc_pace() {
        return initRanks(dc_pace);
    }

    public void setDc_pace(List<RankRemoteItem> dc_pace) {
        this.dc_pace = dc_pace;
    }

    public List<RankRemoteItem> getTyj_pace() {
        return initRanks(tyj_pace);
    }

    public void setTyj_pace(List<RankRemoteItem> tyj_pace) {
        this.tyj_pace = tyj_pace;
    }

    public List<RankRemoteItem> getHdj_max_weight() {
        return initRanks(hdj_max_weight);
    }

    public void setHdj_max_weight(List<RankRemoteItem> hdj_max_weight) {
        this.hdj_max_weight = hdj_max_weight;
    }

    public List<RankRemoteItem> getTotal_capacity() {
        return initRanks(total_capacity);
    }

    public void setTotal_capacity(List<RankRemoteItem> total_capacity) {
        this.total_capacity = total_capacity;
    }

    public List<RankRemoteItem> getSingle_max_capacity() {
        return initRanks(single_max_capacity);
    }

    public void setSingle_max_capacity(List<RankRemoteItem> single_max_capacity) {
        this.single_max_capacity = single_max_capacity;
    }


    private List<RankRemoteItem> initRanks(List<RankRemoteItem> items) {
        if (CollectionsUtil.isEmpty(items)) {
            items = new ArrayList<>();
        }
        int index = 10 - CollectionsUtil.size(items);
        if (CollectionsUtil.size(items) < 10) {
            for (int i = 0; i < index; i++) {
                items.add(new RankRemoteItem());
            }
        } else {
            items = items.subList(0, 10);
        }
        return items;
    }

}
