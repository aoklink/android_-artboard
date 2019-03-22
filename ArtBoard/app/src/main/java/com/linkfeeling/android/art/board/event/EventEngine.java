package com.linkfeeling.android.art.board.event;

import android.util.Log;

public class EventEngine {

    private static EventEngineImpl sEventEngineImpl;

    static {
        sEventEngineImpl = new EventEngineImpl();
    }

    public static void post(String event,Object message){
        sEventEngineImpl.post(event, message);
    }

    public static void postOnUI(String event,Object message){
        Log.d("postOnUI",event+":"+message.toString());
        sEventEngineImpl.postOnUI(event, message);
    }

    public static IPeriodOperator postPeriod(final String event, long period, Runnable runnable,boolean startNow){
        sEventEngineImpl.postPeriod(event, period, runnable,startNow);
        return new IPeriodOperator() {
            @Override
            public void reCall() {
                sEventEngineImpl.reCallPeriod(event);
            }

            @Override
            public void pause() {
                sEventEngineImpl.pausePeriod(event);
            }

            @Override
            public void cancel() {
                sEventEngineImpl.cancelPeriod(event);
            }
        };
    }

    public static IListenerOperator listen(final String event, final IEventListener eventListener){
        sEventEngineImpl.listen(event,eventListener);
        return new IListenerOperator() {
            @Override
            public void cancel() {
                sEventEngineImpl.cancelListen(event, eventListener);
            }
        };
    }
}
