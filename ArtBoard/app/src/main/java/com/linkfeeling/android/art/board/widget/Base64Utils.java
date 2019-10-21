package com.linkfeeling.android.art.board.widget;

import com.link.feeling.framework.KeysConstants;
import com.link.feeling.framework.utils.data.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created on 2019/4/8  9:36
 * chenpan pan.chen@linkfeeling.cn
 */
public final class Base64Utils {


    /**
     * 转码
     *
     * @param value
     * @return
     */
    public static String URLEncoder(String value) {
        try {
           return URLEncoder.encode(value, KeysConstants.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解码
     *
     * @param value
     * @return
     */
    public static String URLDecoder(String value) {
        if (StringUtils.isEmpty(value)){
            return "";
        }
        try {
           return URLDecoder.decode(value, KeysConstants.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
