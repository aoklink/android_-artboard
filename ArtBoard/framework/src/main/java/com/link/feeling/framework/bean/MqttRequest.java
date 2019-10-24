package com.link.feeling.framework.bean;

/**
 * Created on 2019/9/27  10:52
 * chenpan pan.chen@linkfeeling.cn
 */
public final class MqttRequest {

    /**
     * type : 101
     * gym_id : link_test
     */

    private int type ;
    private String gym_id;

    public MqttRequest() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGym_id() {
        return gym_id == null ? "" : gym_id;
    }

    public void setGym_id(String gym_id) {
        this.gym_id = gym_id;
    }

    public MqttRequest(int type, String gym_id) {
        this.type = type;
        this.gym_id = gym_id;
    }
}
