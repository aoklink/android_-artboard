package com.linkfeeling.android.art.board.data.bean.rank;

import android.os.Parcel;
import android.os.Parcelable;

import com.link.feeling.framework.KeysConstants;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/9/27  10:27
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class RankRemoteModule implements Parcelable {


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

    private int id;
    private String img;
    private String unit;
    private List<RankRemoteItem> data;

    public String getUnit() {
        return unit == null ? "" : unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img == null ? "" : img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<RankRemoteItem> getData() {
        return initRanks(data);
    }

    public void setData(List<RankRemoteItem> data) {
        this.data = data;
    }

    private List<RankRemoteItem> initRanks(List<RankRemoteItem> items) {
        if (CollectionsUtil.isEmpty(items)) {
            items = new ArrayList<>();
        }
        int index = KeysConstants.RANK_ITEM - CollectionsUtil.size(items);
        if (CollectionsUtil.size(items) < KeysConstants.RANK_ITEM) {
            for (int i = 0; i < index; i++) {
                items.add(new RankRemoteItem());
            }
        } else {
            items = items.subList(0, KeysConstants.RANK_ITEM);
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.img);
        dest.writeTypedList(this.data);
    }

    public RankRemoteModule() {
    }

    protected RankRemoteModule(Parcel in) {
        this.id = in.readInt();
        this.img = in.readString();
        this.data = in.createTypedArrayList(RankRemoteItem.CREATOR);
    }

    public static final Parcelable.Creator<RankRemoteModule> CREATOR = new Parcelable.Creator<RankRemoteModule>() {
        @Override
        public RankRemoteModule createFromParcel(Parcel source) {
            return new RankRemoteModule(source);
        }

        @Override
        public RankRemoteModule[] newArray(int size) {
            return new RankRemoteModule[size];
        }
    };
}
