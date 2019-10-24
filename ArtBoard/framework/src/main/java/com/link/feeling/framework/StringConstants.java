package com.link.feeling.framework;

/**
 * Created on 2019/9/26  16:04
 * chenpan pan.chen@linkfeeling.cn
 */
public final class StringConstants {

    public static String matchHolder(int index) {
        switch (index) {
            case 1:
                return "kcal";
            case 2:
                return "天";
            case 3:
                return "";
            case 4:
                return "公里";
            case 5:
                return "公里";
            case 6:
                return "公里";
            case 7:
                return "KG";
            case 8:
                return "KG";
            case 9:
                return "KG";
        }
        return "";
    }


    public static String matchGymId(int id) {
        switch (id) {
            case 0:
                return "link_office";
           case 1:
                return "fitting_gym_xixi";
           case 2:
                return "rl_jinhua_fitness";
           case 3:
                return "gaote_fitness";
        }
        return "link_office";
    }

}
