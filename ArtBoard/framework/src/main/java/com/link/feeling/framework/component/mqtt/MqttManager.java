package com.link.feeling.framework.component.mqtt;

import com.alibaba.fastjson.JSON;
import com.link.feeling.framework.KeysConstants;
import com.link.feeling.framework.base.BaseApplication;
import com.link.feeling.framework.bean.MqttRequest;
import com.link.feeling.framework.utils.data.DeviceUtils;
import com.link.feeling.framework.utils.data.L;
import com.link.feeling.framework.utils.data.StringUtils;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created on 2019/9/9  11:29
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class MqttManager {

    private static final String TAG = "MqttManager";

    private MqttAndroidClient mqttAndroidClient;

    private int mType;

    public static MqttManager newInstance() {
        return new MqttManager();
    }

    public void connect(MqttCallbackExtended callback, int type) {
        mType = type;
        if (mqttAndroidClient == null) {
            mqttAndroidClient = new MqttAndroidClient(BaseApplication.getAppContext(), KeysConstants.SERVER_URL, KeysConstants.GID + DeviceUtils.getMac());
        }
        mqttAndroidClient.setCallback(callback);
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setConnectionTimeout(10);
        mqttConnectOptions.setKeepAliveInterval(15);
        mqttConnectOptions.setMaxInflight(1000);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        try {
            mqttConnectOptions.setUserName(KeysConstants.SIGNATURE + KeysConstants.ACCESS_KEY + KeysConstants.SEPARATOR + KeysConstants.INSTANCE_ID);
            mqttConnectOptions.setPassword(Tool.macSignature(KeysConstants.GID + DeviceUtils.getMac(), KeysConstants.SECRET_KEY).toCharArray());
        } catch (Exception e) {
            L.e(TAG, "exception:setPassword", e);
        }

        doConnect(mqttConnectOptions);

    }

    private void doConnect(MqttConnectOptions mqttConnectOptions) {
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    L.e(TAG, "connect:onSuccess");
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    L.e(TAG, "connect:onFailure", exception);
                    doConnect(mqttConnectOptions);
                }
            });
        } catch (Exception e) {
            L.e(TAG, "connect:exception", e);
        }
    }

    public void subscribeToTopic() {
        try {
            final String topicFilter[] = {KeysConstants.TOPIC};
            final int[] qos = {1};
            mqttAndroidClient.subscribe(topicFilter, qos, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    L.e(TAG, "subscribe:success");
                    publishMessage(JSON.toJSONString(new MqttRequest(mType)));
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    L.e(TAG, "subscribe:failed", exception);
                }
            });

        } catch (Exception ex) {
            L.e(TAG, "subscribe:exception", ex);
        }
    }


    public void publishMessage(String json) {
        if (StringUtils.isJsonEmpty(json)) {
            return;
        }
        try {
            MqttMessage message = new MqttMessage();
            final String msg = json;
            message.setPayload(msg.getBytes());
            mqttAndroidClient.publish(KeysConstants.TOPIC_FATHER, message, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    L.e(TAG, "publish:success:" + msg);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    L.e(TAG, "publish:failed:" + msg);
                }
            });
        } catch (Exception e) {
            L.e(TAG, "publish:exception", e);
        }

    }


    public void destroy() {
        try {
            mqttAndroidClient.unsubscribe(KeysConstants.TOPIC);
            mqttAndroidClient.disconnect();
//            mqttAndroidClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
