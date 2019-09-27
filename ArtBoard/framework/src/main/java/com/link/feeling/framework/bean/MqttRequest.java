package com.link.feeling.framework.bean;

import com.link.feeling.framework.KeysConstants;

/**
 * Created on 2019/9/27  10:52
 * chenpan pan.chen@linkfeeling.cn
 */
public final class MqttRequest {

    /**
     * type : 101
     * gym_id : link_test
     */

    private int type;
    private String gym_id = KeysConstants.GYM;

    public MqttRequest(int type) {
        this.type = type;
    }
}
