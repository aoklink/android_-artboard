package com.linkfeeling.android.art.board.utils;

import java.text.SimpleDateFormat;

/**
 * Created on 2019/1/29  15:08
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("all")
public final class DateUtils {

    public static String PATTERN = "HH:mm";
    private static SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);

    /**
     * 转换
     *
     * @param mm
     * @return
     */
    public static String formatHour(long mm) {
        return sdf.format(mm);
    }

    //
//    public static void main(String[] args) {
//        System.out.println(formatHour(System.currentTimeMillis()));
//    }

}
