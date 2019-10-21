package com.linkfeeling.android.art.board.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created on 2019/10/10  14:22
 * chenpan pan.chen@linkfeeling.cn
 */
public final class CalculateUtils {

    static final DecimalFormat format = new DecimalFormat();

    public static String formatSign(int price) {
        format.setGroupingSize(3);
        format.setMaximumFractionDigits(0);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(price);
    }

    public static String formatSign(float price) {
        format.setGroupingSize(3);
        format.setMaximumFractionDigits(0);
//        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(price);
    }
}
