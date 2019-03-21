package com.linkfeeling.android.art.board.core.user.board;

public class UserBoardItem {
    private int calories;
    private int heartRate;
    private String userIcon;
    private String userNick;

    public UserBoardItem(int calories, int heartRate, String userIcon, String userNick) {
        this.calories = calories;
        this.heartRate = heartRate;
        this.userIcon = userIcon;
        this.userNick = userNick;
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
}
