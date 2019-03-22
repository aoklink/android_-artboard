package com.linkfeeling.android.art.board.event;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class EventExecutor {
    private Handler mUIHandler;
    private Handler mWorkHandler;
    private Handler mWatchDogHandler;

    private HandlerThread mWorkThread;
    private HandlerThread mWatchDogThread;


    public EventExecutor() {
        mUIHandler = new Handler(Looper.getMainLooper());
        mWorkThread =  new HandlerThread("work"){
            @Override
            protected void onLooperPrepared() {
                mWorkHandler = new Handler(getLooper());
            }
        };
        mWatchDogThread = new HandlerThread("watch_dog"){
            @Override
            protected void onLooperPrepared() {
                mWatchDogHandler = new Handler(getLooper());
            }
        };
    }

    public void start(){
        mWorkThread.start();
        while (mWorkThread==null);
        mWatchDogThread.start();
        while (mWatchDogHandler==null);
    }

    public void quit(){
        mWorkThread.quitSafely();
        mWatchDogThread.quitSafely();
    }

    public Handler getUIHandler() {
        return mUIHandler;
    }

    public Handler getWorkHandler() {
        return mWorkHandler;
    }

    public Handler getWatchDogHandler() {
        return mWatchDogHandler;
    }
}
