package com.linkfeeling.android.art.board.data.bean.rank;

import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.StringUtils;

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
    private List<RankRemoteItem> duration;
    private List<RankRemoteItem> pbj_distance;
    private List<RankRemoteItem> dc_distance;
    private List<RankRemoteItem> tyj_distance;
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

    public List<RankRemoteItem> getDuration() {
        return initRanks(duration);
    }

    public void setDuration(List<RankRemoteItem> duration) {
        this.duration = duration;
    }

    public List<RankRemoteItem> getPbj_distance() {
        return initRanks(pbj_distance);
    }

    public void setPbj_distance(List<RankRemoteItem> pbj_distance) {
        this.pbj_distance = pbj_distance;
    }

    public List<RankRemoteItem> getDc_distance() {
        return initRanks(dc_distance);
    }

    public void setDc_distance(List<RankRemoteItem> dc_distance) {
        this.dc_distance = dc_distance;
    }

    public List<RankRemoteItem> getTyj_distance() {
        return initRanks(tyj_distance);
    }

    public void setTyj_distance(List<RankRemoteItem> tyj_distance) {
        this.tyj_distance = tyj_distance;
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
        for (int i = 0; i < 5; i++) {
            items.add(new RankRemoteItem());
        }
        return items;
    }

    private boolean isGo(List<RankRemoteItem> items) {
        if (CollectionsUtil.isEmpty(items)) {
            return true;
        }
        int index = 0;
        for (RankRemoteItem item : items) {
            if (StringUtils.isNotEmpty(item.getUid())) {
                index++;
            }
        }
        return index <= 5;
    }
}
