package com.linkfeeling.android.art.board.core.data;

public class ArtBoardDataCenterHolder {
    private static final ArtBoardDataCenter sInstance = new ArtBoardDataCenter();

    public static ArtBoardDataCenter getInstance() {
        return sInstance;
    }
}
