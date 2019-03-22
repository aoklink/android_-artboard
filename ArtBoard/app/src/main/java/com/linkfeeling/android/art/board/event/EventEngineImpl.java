package com.linkfeeling.android.art.board.event;

import com.linkfeeling.android.art.board.event.worker.PeriodWorker;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventEngineImpl {
    private Map<String,Set<IEventListener>> eventListenerMap;
    private Map<String,PeriodWorker> periodWorkerMap;
    private EventExecutor eventExecutor;

    public EventEngineImpl() {
        eventListenerMap = new HashMap<>();
        eventExecutor = new EventExecutor();
        periodWorkerMap = new HashMap<>();
        eventExecutor.start();
    }

    public void post(String event,final Object message){
        for(final IEventListener eventListener : ensureListenerSet(event,false)){
            eventExecutor.getWorkHandler().post(new Runnable() {
                @Override
                public void run() {
                    eventListener.on(message);
                }
            });
        }
    }

    public void postOnUI(String event, final Object message){
        for(final IEventListener eventListener : ensureListenerSet(event,false)){
            eventExecutor.getUIHandler().post(new Runnable() {
                @Override
                public void run() {
                    eventListener.on(message);
                }
            });
        }
    }

    public void postPeriod(String event,long period,Runnable runnable,boolean startNow){
        PeriodWorker periodWorker = new PeriodWorker(eventExecutor.getWorkHandler(),runnable,period);
        periodWorkerMap.put(event,periodWorker);
        if(startNow){
            eventExecutor.getWorkHandler().post(periodWorker);
        }
    }


    public void cancelPeriod(String event) {
        PeriodWorker periodWorker = periodWorkerMap.remove(event);
        if(periodWorker!=null){
            periodWorker.cancel();
        }
    }

    public void pausePeriod(String event) {
        PeriodWorker periodWorker = periodWorkerMap.get(event);
        if(periodWorker!=null){
            periodWorker.pause();
        }
    }

    public void listen(String event, IEventListener eventListener) {
        ensureListenerSet(event,true).add(eventListener);
    }

    public void cancelListen(String event, IEventListener eventListener){
        ensureListenerSet(event,false).remove(eventListener);
    }

    private Set<IEventListener> ensureListenerSet(String event,boolean create){
        Set<IEventListener> set = eventListenerMap.get(event);
        if(set==null){
            if(create){
                set = new HashSet<>();
                eventListenerMap.put(event,set);
                return set;
            }else{
                return Collections.emptySet();
            }
        }else{
            return set;
        }
    }

    public void reCallPeriod(String event) {
        PeriodWorker periodWorker = periodWorkerMap.get(event);
        if(periodWorker!=null){
            periodWorker.reCall();
        }
    }

}
