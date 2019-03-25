package com.linkfeeling.android.art.board.third.network;

import com.linkfeeling.android.art.board.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HttpSupportUtil extends HttpUtil{


    private static final boolean test = BuildConfig.DEBUG;
    private static Map<String,Object> tmpArgMap = new HashMap<>();


    public static String postJson(String url,String body)throws Exception{

        String result =  post(url,"application/json",body);
        return result;
//        if(!test){
//            String result =  post(url,"application/json",body);
//            return result;
//        }else{
//            Thread.sleep(new Random().nextInt(3000));
//            return testJson();
//        }
    }


    private static String testJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code","200");
            jsonObject.put("msg","OK");
            JSONArray jsonArray = new JSONArray();
            Random random = new Random();
            int nowCount = 0;
            Object lastCountObj = tmpArgMap.get("last_count");
            if(lastCountObj==null){
                nowCount = 8;
            }else{
                Integer lastCount = (Integer) lastCountObj;
                nowCount = lastCount+(random.nextInt(2)%2==0?-1:1);
            }
            tmpArgMap.put("last_count",nowCount);

            for (int i = 0; i < nowCount; i++) {
                JSONObject item = new JSONObject();
                item.put("kc",random.nextInt(1200));
                item.put("uid", "xxxxx");
                item.put("heart_rate", random.nextInt(200));
                item.put("user_name", "jackson"+random.nextInt(10));
                item.put("result", random.nextInt(6));
                jsonArray.put(item);
            }
            jsonObject.put("data",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return jsonObject.toString();
    }
}
