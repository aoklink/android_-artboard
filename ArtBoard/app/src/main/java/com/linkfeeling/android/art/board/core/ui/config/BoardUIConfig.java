package com.linkfeeling.android.art.board.core.ui.config;

import android.content.Context;
import android.content.res.Resources;

import com.linkfeeling.android.art.board.R;

public class BoardUIConfig {
    private static int sUserBoardRadius = 4;

    private static int sUserSportSummaryItemRadius = 14;

    public static void load(Context context){
        Resources resources = context.getResources();
        sUserBoardRadius = resources.getDimensionPixelOffset(R.dimen.user_board_item_radius);
        sUserSportSummaryItemRadius = resources.getDimensionPixelOffset(R.dimen.user_sport_summary_item_radius);
    }

    public static int getUserBoardRadius() {
        return sUserBoardRadius;
    }

    public static int getUserSportSummaryItemRadius() {
        return sUserSportSummaryItemRadius;
    }
}
