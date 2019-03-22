package com.linkfeeling.android.art.board.event.worker;

import android.os.Handler;

public class PeriodWorker implements Runnable {
    private Handler mHandler;
    private Runnable target;
    private long period;
    private boolean needCancel;

    public PeriodWorker(Handler mHandler, Runnable target, long period) {
        this.mHandler = mHandler;
        this.target = target;
        this.period = period;
        needCancel = false;
    }

    @Override
    public void run() {
        mHandler.post(periodCaller);
    }

    public void cancel(){
        needCancel = true;
    }

    public void reCall(){
        mHandler.removeCallbacks(periodCaller);
        mHandler.post(periodCaller);
    }

    public void pause(){
        mHandler.removeCallbacks(periodCaller);
    }

    private Runnable periodCaller = new Runnable() {
        @Override
        public void run() {
            if(needCancel) return;
            target.run();
            mHandler.removeCallbacks(periodCaller);
            mHandler.postDelayed(periodCaller,period);
        }
    };
}
