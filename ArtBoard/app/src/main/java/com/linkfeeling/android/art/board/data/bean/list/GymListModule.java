package com.linkfeeling.android.art.board.data.bean.list;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

/**
 * Created on 2019/8/16  10:47
 * chenpan pan.chen@linkfeeling.cn
 */
public final class GymListModule implements Parcelable {

    private String gym;
    private String gymId;
    @DrawableRes
    private int gymLogo;


    public GymListModule(String gym, String gymId, int gymLogo) {
        this.gym = gym;
        this.gymId = gymId;
        this.gymLogo = gymLogo;
    }

    public String getGym() {
        return gym == null ? "" : gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getGymId() {
        return gymId == null ? "" : gymId;
    }

    public void setGymId(String gymId) {
        this.gymId = gymId;
    }

    public int getGymLogo() {
        return gymLogo;
    }

    public void setGymLogo(int gymLogo) {
        this.gymLogo = gymLogo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gym);
        dest.writeString(this.gymId);
        dest.writeInt(this.gymLogo);
    }

    protected GymListModule(Parcel in) {
        this.gym = in.readString();
        this.gymId = in.readString();
        this.gymLogo = in.readInt();
    }

    public static final Creator<GymListModule> CREATOR = new Creator<GymListModule>() {
        @Override
        public GymListModule createFromParcel(Parcel source) {
            return new GymListModule(source);
        }

        @Override
        public GymListModule[] newArray(int size) {
            return new GymListModule[size];
        }
    };
}
