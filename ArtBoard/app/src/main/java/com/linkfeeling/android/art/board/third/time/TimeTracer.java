package com.linkfeeling.android.art.board.third.time;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class TimeTracer {

    private static final Map<String,Long> sTracerMap = new HashMap<>();

    public static void start(String sign) {
        sTracerMap.put(sign,System.currentTimeMillis());
    }

    public static void end(String sign) {
        long endTime = System.currentTimeMillis();
        Long startTime = sTracerMap.get(sign);
        if(startTime==null){
            return;
        }
        Log.d("TimeTracer","action:"+sign+" use time:"+(endTime-startTime)+"ms");
    }
}
