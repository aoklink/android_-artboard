package com.linkfeeling.android.art.board.core.user.board.bean.support;

import com.linkfeeling.android.art.board.core.user.board.UserBoardItem;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class UserBoardItemBeanFactory {

    private static final Queue<UserBoardItem> sCache = new LinkedBlockingDeque<>();

    public static UserBoardItem getCleanInstance(){
        if(sCache.isEmpty()){
            return new UserBoardItem();
        }else{
            return sCache.poll();
        }
    }

    public static void recycle(Collection<UserBoardItem> userBoardItems){
        for(UserBoardItem userBoardItem: userBoardItems){
            sCache.add(clean(userBoardItem));
        }
    }

    /**
     *  private int calories;
     *     private int heartRate;
     *     private String userIcon;
     *     private String userNick;
     *     private int result;
     *     private String uid;
     * @param userBoardItem
     * @return
     */
    private static UserBoardItem clean(UserBoardItem userBoardItem){
        userBoardItem.setCalories(0);
        userBoardItem.setHeartRate(0);
        userBoardItem.setUserIcon("");
        userBoardItem.setUserNick("");
        userBoardItem.setResult(0);
        userBoardItem.setUid("");
        return userBoardItem;
    }
}
