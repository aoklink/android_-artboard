package com.linkfeeling.android.art.board;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created on 2019/4/28  15:29
 * chenpan pan.chen@linkfeeling.cn
 */
public final class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG) {
            CrashReport.initCrashReport(getApplicationContext(), "40cc86aadc", false);
        }
    }
}
