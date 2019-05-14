package com.linkfeeling.android.art.board.widget;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

public class BackgroundProvider {

    public static final String AT_USER_SPORT = "user_sport";
    public static final String AT_USER_SPORT_SUMMARY = "user_sport_summary";

    private static Map<String,IBackgroundDrawableProvider> backgroundDrawableProviderMap = new HashMap<>();

    public static Drawable get(String where, String name,int index){
       return ensure(where).provide(name,index);
    }

    private static IBackgroundDrawableProvider ensure(String where){
        if(backgroundDrawableProviderMap.get(where)==null){
            if(AT_USER_SPORT.equals(where)){
                IBackgroundDrawableProvider drawableProvider = new UserSportBgProvider();
                backgroundDrawableProviderMap.put(where,drawableProvider);
                return drawableProvider;
            }else if(AT_USER_SPORT_SUMMARY.equals(where)){
                IBackgroundDrawableProvider drawableProvider = new UserSportSummaryItemBgProvider();
                backgroundDrawableProviderMap.put(where,drawableProvider);
                return drawableProvider;
            }else {
                return null;
            }
        }else{
            return backgroundDrawableProviderMap.get(where);
        }
    }
}
