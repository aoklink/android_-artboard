package com.linkfeeling.android.art.board.data.bean.rank;

import android.os.Parcel;
import android.os.Parcelable;

import com.link.feeling.framework.utils.data.StringUtils;
import com.link.feeling.framework.widgets.NumParseUtil;
import com.linkfeeling.android.art.board.utils.CalculateUtils;
import com.linkfeeling.android.art.board.widget.Base64Utils;

import java.util.Locale;

/**
 * Created on 2019/9/27  10:23
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class RankRemoteItem implements Parcelable {

    /**
     * user_name :
     * head_icon :
     * uid :
     * value : 123
     */

    private String user_name;
    private String head_icon;
    private String uid;
    private String value;
    private int index;

    public RankRemoteItem() {
    }

    public RankRemoteItem(String user_name, String head_icon, String uid, String value ,int index) {
        this.user_name = user_name;
        this.head_icon = head_icon;
        this.uid = uid;
        this.value = value;
        this.index = index;
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

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFormatValue(int index) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        switch (index) {
            case 1:
            case 2:
                return CalculateUtils.formatSign(NumParseUtil.parseInt(value));
            case 3:
                return formatDuration();
            case 4:
            case 5:
            case 6:
                return value;
            case 7:
            case 8:
            case 9:
                return value;
        }
        return value;
    }

    private String formatDuration() {
        long time = NumParseUtil.parseLong(value);
        if (time < 60) {
            return String.format(Locale.getDefault(), "00:00:%02d", time % 60);
        } else if (time < 3600) {
            return String.format(Locale.getDefault(), "00:%02d:%02d", time / 60, time % 60);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", time / 3600, time % 3600 / 60, time % 60);
        }
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_name);
        dest.writeString(this.head_icon);
        dest.writeString(this.uid);
        dest.writeString(this.value);
    }

    protected RankRemoteItem(Parcel in) {
        this.user_name = in.readString();
        this.head_icon = in.readString();
        this.uid = in.readString();
        this.value = in.readString();
    }

    public static final Creator<RankRemoteItem> CREATOR = new Creator<RankRemoteItem>() {
        @Override
        public RankRemoteItem createFromParcel(Parcel source) {
            return new RankRemoteItem(source);
        }

        @Override
        public RankRemoteItem[] newArray(int size) {
            return new RankRemoteItem[size];
        }
    };
}
