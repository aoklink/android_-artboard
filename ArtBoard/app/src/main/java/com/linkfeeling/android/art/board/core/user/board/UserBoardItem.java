package com.linkfeeling.android.art.board.core.user.board;

public class UserBoardItem {
    private int calories;
    private int heartRate;
    private String userIcon;
    private String userNick;
    private int result;
    private String uid;

    public UserBoardItem(int calories, int heartRate, String userIcon, String userNick) {
        this.calories = calories;
        this.heartRate = heartRate;
        this.userIcon = userIcon;
        this.userNick = userNick;
    }

    public UserBoardItem(int calories, int heartRate, String userIcon, String userNick, int result, String uid) {
        this.calories = calories;
        this.heartRate = heartRate;
        this.userIcon = userIcon;
        this.userNick = userNick;
        this.result = result;
        this.uid = uid;
    }

    public UserBoardItem() {

    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
