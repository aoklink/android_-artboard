package com.link.feeling.framework.component.net.config;

import okhttp3.MediaType;

/**
 * Created on 2019/1/7  11:00
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class ServerConfig {

    /**
     * 生产环境  0
     * 测试环境  1
     * 开发环境  2
     */
    public final static int ENV_PROD = 0;
    public final static int ENV_TEST = 1;
    public final static int ENV_DEV = 2;

    public static String HOST;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    public static String WEB_DETAIL;

    //在build.gradle的productFlavors中控制
    public static int env = 1;
    public static boolean debugEnable = true;

    /**
     * Application.create 中 调用
     * <p>
     * 拓展类...
     */
    public static void initEnv(int appEnv, boolean enableDebug) {
        env = appEnv;
        debugEnable = enableDebug;

        if (ENV_PROD == appEnv) {
            HOST = "https://ll.linkfeeling.cn/api/";
            WEB_DETAIL = "https://ll.linkfeeling.cn/wx/share.html?";
        } else if (ENV_TEST == appEnv) {
            HOST = "https://ll.linkfeeling.cn/api/";
            WEB_DETAIL = "https://ll.linkfeeling.cn/wx/share.html?";
        } else {
            HOST = "https://dev.linkfeeling.cn/api/";
            WEB_DETAIL = "https://ll.linkfeeling.cn/wx/share.html?";
        }
    }


}
