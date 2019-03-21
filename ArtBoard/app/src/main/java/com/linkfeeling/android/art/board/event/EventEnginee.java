package com.linkfeeling.android.art.board.event;

public class EventEnginee {

    public static void post(String event,Object message){

    }

    public static IListenerOperator listen(String event){

        return new IListenerOperator() {
            @Override
            public void cancel() {

            }
        };
    }
}
