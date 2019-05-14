package com.linkfeeling.android.art.board;

import com.link.feeling.framework.base.BaseApplication;
import com.link.feeling.framework.component.net.config.ServerConfig;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created on 2019/4/28  15:29
 * chenpan pan.chen@linkfeeling.cn
 */
public final class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ServerConfig.initEnv(BuildConfig.ENV , BuildConfig.ENABLE_DEBUG);
        CrashReport.initCrashReport(getApplicationContext(), "40cc86aadc", false);
    }
}
