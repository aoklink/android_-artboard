package com.linkfeeling.android.art.board.core.data.trans;

import com.linkfeeling.android.art.board.core.user.board.UserBoardItem;
import com.linkfeeling.android.art.board.core.user.board.bean.support.UserBoardItemBeanFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserBoardItemTrans {
    /**
     * {{
     *      "code": "200",
     *      "msg":"ok",
     *      "data":"[{
     *                     "kc": "1240",
     *                     "heart_rate":"130",
     *                     "uid": "xxxxx",
     *                     "user_name":"jack",
     *                     "head_icon":"https://img.linkfeeling.cn/img/oo.png",
     *                     "result":"1"
     *                 },
     *                 {
     *                     "kc": "1200",
     *                     "uid": "xxxxx",
     *                     "heart_rate":"125",
     *                     "user_name":"jackson",
     *                     "head_icon":"https://img.linkfeeling.cn/img/oo.png",
     *                     "result":"2"
     *                 }]"
     *   }
     * @param jsonObject
     * @return
     */
    public static UserBoardItem fromJson(JSONObject jsonObject) {
        UserBoardItem userBoardItem = UserBoardItemBeanFactory.getCleanInstance();
        userBoardItem.setUid(getString(jsonObject,"uid"));
        userBoardItem.setUserNick(getString(jsonObject,"user_name"));
        userBoardItem.setUserIcon(getString(jsonObject,"head_icon"));
        userBoardItem.setHeartRate(getInt(jsonObject,"heart_rate"));
        userBoardItem.setCalorie(getInt(jsonObject,"calorie"));
        userBoardItem.setResult(getInt(jsonObject,"result"));
        return userBoardItem;
    }


    private static String getString(JSONObject jsonObject,String name){
        try {
            return jsonObject.getString(name);
        } catch (JSONException e) {
            return "";
        }
    }

    private static int getInt(JSONObject jsonObject,String name){
        try {
            return jsonObject.getInt(name);
        } catch (JSONException e) {
            return 0;
        }
    }

    public static List<UserBoardItem> fromJsonArray(JSONArray jsonArray){
        List<UserBoardItem> userBoardItems = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                userBoardItems.add(fromJson(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        return userBoardItems;
    }
}
