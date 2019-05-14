package com.link.feeling.framework.base;

import android.content.Context;

import com.link.feeling.framework.utils.ui.ActivityUtils;

import androidx.multidex.MultiDexApplication;
/**
 * Created on 2019/1/3  15:06
 * chenpan pan.chen@linkfeeling.cn
 */
public class BaseApplication extends MultiDexApplication {

    // context
    private static BaseApplication sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        ActivityUtils.init(this);

    }

    public static Context getAppContext() {
        return sContext;
    }

    @Override
    public void onLowMemory() {
        System.gc();
        System.runFinalization();
        System.gc();
        super.onLowMemory();
    }
}
