package com.link.feeling.framework;

/**
 * Created on 2019/1/3  18:54
 * chenpan pan.chen@linkfeeling.cn
 * <p>
 * 服务器请求字段
 */
@SuppressWarnings("unused")
public interface KeysConstants {

    // 字体
    String NUMBER_FONT = "DIN-BlackItalic.otf";

    // 网络设置相关
    String USER_AGENT = "User-Agent";
    String CONTENT_TYPE = "Content-Type";
    String APPLICATION = "application/x-www-form-urlencoded;charset=UTF-8";
    String UTF_8 = "UTF-8";

    // 	客户端当前系统时间,取毫秒
    String REQUEST_TIME = "request_time";

    // 	当前平台(小程序:small_program, 安卓:android,苹果:ios,网页:h5)
    String PLATFORM = "platform";

    // 	生成过程:md5(product_id + “:” + user_type + “:” + request_time)
    String TK = "tk";

    //  当前网络(3G,4G,5G,wifi)
    String NETWORK = "network";

    // 	产品id
    String PRODUCT_ID = "product_id";

    // 客户端版本号
    String APP_VERSION = "app_version";


    String ANDROID = "android";

    String LINK_FEELING = "Link-TV";

    String GYM_NAME = "gym_name";

    String INDEX = "index";
    String INDEX1 = "index1";
    String INDEX2 = "index2";
    String INDEX3 = "index3";


    // prod
//    String SERVER_URL = "tcp://post-cn-v0h19rdwa01.mqtt.aliyuncs.com";
//    String INSTANCE_ID = "post-cn-v0h19rdwa01";

    String SERVER_URL = "tcp://post-cn-0pp1bk98n05.mqtt.aliyuncs.com";
    String INSTANCE_ID = "post-cn-0pp1bk98n05";

    String ACCESS_KEY = "LTAI4FeptrQPpNaeBLzsgkMW";
    String SECRET_KEY = "5VNrknvqHVZqFWrPnb3SvQIYqKLrn8";

//        String GYM = "rl_jinhua_fitness";
    String GYM = "link_test";
    String TOPIC = "heart_rate_topic/" + GYM;
    String TOPIC_FATHER = "heart_rate_topic";

    String SIGNATURE = "Signature|";
    String SEPARATOR = "|";
    String GID = "GID_LK_HEART_RATE@@@_" + GYM + "_";


    int RANK_ITEM = 11;

//    测试环境:
//    instanceId:post-cn-0pp1bk98n05
//    ServiceUrl:post-cn-0pp1bk98n05.mqtt.aliyuncs.com
//    线上环境:
//    instanceId:post-cn-v0h19rdwa01
//    ServiceUrl:post-cn-v0h19rdwa01.mqtt.aliyuncs.com
}
