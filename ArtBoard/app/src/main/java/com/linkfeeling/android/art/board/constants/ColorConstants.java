package com.linkfeeling.android.art.board.constants;

/**
 * Created on 2019/3/1  15:33
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class ColorConstants {
    // FF CC 7F 33

    private static final int COLOR1 = 0x331A78FF;
    private static final int COLOR2 = 0x33AB49FC;
    private static final int COLOR3 = 0x333FC193;
    private static final int COLOR4 = 0x33FFA941;
    private static final int COLOR5 = 0x33FF704E;
    private static final int COLOR6 = 0x33F54646;

    private static final int[] COLORS1 = new int[]{0xFF1A78FF, 0xCC1A78FF, 0x7F1A78FF};
    private static final int[] COLORS2 = new int[]{0xFFAB49FC, 0xCCAB49FC, 0x7FAB49FC};
    private static final int[] COLORS3 = new int[]{0xFF3FC193, 0xCC3FC193, 0x7F3FC193};
    private static final int[] COLORS4 = new int[]{0xFFFFA941, 0xCCFFA941, 0x7FFFA941};
    private static final int[] COLORS5 = new int[]{0xFFFF704E, 0xCCFF704E, 0x7FFF704E};
    private static final int[] COLORS6 = new int[]{0xFFF54646, 0xCCF54646, 0x7FF54646};


    private ColorConstants() {
    }

    //*****************************************************************************
    // 设备图片
    public static int loadColor(int percent) {
        if (percent < 40) {
            return COLOR1;
        } else if (percent < 56) {
            return COLOR2;
        } else if (percent < 70) {
            return COLOR3;
        } else if (percent < 80) {
            return COLOR4;
        } else if (percent < 90) {
            return COLOR5;
        } else {
            return COLOR6;
        }
    }

    // 设备图片
    public static int[] loadColors(int percent) {
        if (percent < 40) {
            return COLORS1;
        } else if (percent < 56) {
            return COLORS2;
        } else if (percent < 70) {
            return COLORS3;
        } else if (percent < 80) {
            return COLORS4;
        } else if (percent < 90) {
            return COLORS5;
        } else {
            return COLORS6;
        }
    }


}
