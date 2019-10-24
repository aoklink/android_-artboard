package com.linkfeeling.android.art.board.constants;

import com.linkfeeling.android.art.board.R;

/**
 * Created on 2019/6/10  11:24
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("all")
public final class ImageConstants {

    private static final String s0 = "0";
    private static final String s1 = "1";
    private static final String s2 = "2";
    private static final String s3 = "3";
    private static final String s4 = "4";
    private static final String s5 = "5";
    private static final String s6 = "6";
    private static final String s7 = "7";
    private static final String s8 = "8";
    private static final String s9 = "9";


    /**
     * 数字
     *
     * @param num
     * @return
     */
    public static int matchNumberImage(String num) {
        switch (num) {
            case s0:
                return R.mipmap.number0;
            case s1:
                return R.mipmap.number1;
            case s2:
                return R.mipmap.number2;
            case s3:
                return R.mipmap.number3;
            case s4:
                return R.mipmap.number4;
            case s5:
                return R.mipmap.number5;
            case s6:
                return R.mipmap.number6;
            case s7:
                return R.mipmap.number7;
            case s8:
                return R.mipmap.number8;
            case s9:
                return R.mipmap.number9;
        }
        return 0;
    }

    public static int matchRankImage(int num) {
        switch (num) {
            case 1:
                return R.mipmap.rank1;
            case 2:
                return R.mipmap.rank2;
            case 3:
                return R.mipmap.rank3;
            case 4:
                return R.mipmap.rank4;
            case 5:
                return R.mipmap.rank5;
            case 6:
                return R.mipmap.rank6;
            case 7:
                return R.mipmap.rank7;
            case 8:
                return R.mipmap.rank8;
            case 9:
                return R.mipmap.rank9;
            case 10:
                return R.mipmap.rank10;
        }
        return 0;
    }

    public static int matchRankLogo(int num) {
        switch (num) {

            case 1:
                return R.mipmap.icon_rank1;
            case 2:
                return R.mipmap.icon_rank2;
            case 3:
                return R.mipmap.icon_rank3;
            case 4:
                return R.mipmap.icon_rank4;
            case 5:
                return R.mipmap.icon_rank5;
            case 6:
                return R.mipmap.icon_rank6;
            case 7:
                return R.mipmap.icon_rank7;
            case 8:
                return R.mipmap.icon_rank8;
            case 9:
                return R.mipmap.icon_rank9;
        }
        return R.mipmap.rank10;
    }


    public static int matchGymLogo(int gymId){
        switch (gymId) {
            case 0:
                return R.mipmap.icon_gym_logo;
            case 1:
                return R.mipmap.gym_logo_xixi;
            case 2:
                return R.mipmap.gym_logo_ruili;
            case 3:
                return R.mipmap.gym_logo_gaote;
        }
        return 0;
    }
}
