package com.linkfeeling.android.art.board.core.data;

import com.linkfeeling.android.art.board.BuildConfig;
import com.linkfeeling.android.art.board.core.event.IEventManifest;
import com.linkfeeling.android.art.board.event.EventEngine;
import com.linkfeeling.android.art.board.event.IPeriodOperator;
import com.linkfeeling.android.art.board.third.network.HttpSupportUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ArtBoardDataCenter {

    private IPeriodOperator getDataPeriodOperator;

    private boolean stillFail = true;
    private boolean executeOnce = false;

    public void install(){
        stillFail = true;
        executeOnce = false;
        getDataPeriodOperator = EventEngine.postPeriod(IEventManifest.PERIOD_GET_DATA, 1000, new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject=null;
                try {
                    String result = HttpSupportUtil.postJson("https://ll.linkfeeling.cn/api/gym/artboard/data",
                            getRequestBody());
                    jsonObject = new JSONObject(result);
                    EventEngine.postOnUI(IEventManifest.REFRESH_USER_BOARD,jsonObject);
                    if("200".equals(jsonObject.getString("code"))){
                        if(stillFail){
                            stillFail = false;
                        }
                    }
                } catch (Exception e) {
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("exception",e.getMessage());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                }
                EventEngine.postOnUI(IEventManifest.REFRESH_USER_BOARD,jsonObject);
            }
        },false);
    }

    public void tryAgain(){
        getDataPeriodOperator.reCall();
    }

    public void resume(){
        if(!executeOnce){
            getDataPeriodOperator.reCall();
            executeOnce = true;
        }else{
            if(!stillFail){
                getDataPeriodOperator.reCall();
            }
        }

    }

    public void pause() {
        getDataPeriodOperator.pause();
    }

    private String getRequestBody(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gym_name",BuildConfig.GYM_NAME);
            jsonObject.put("device_name",BuildConfig.DEVICE_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public void stop(){
        getDataPeriodOperator.cancel();
    }
}
