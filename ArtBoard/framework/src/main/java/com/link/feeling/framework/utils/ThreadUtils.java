package com.link.feeling.framework.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2019/8/20  19:11
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class ThreadUtils {

    private static ThreadPoolExecutor sThreadPoolExecutor;
    private static Handler sMainThreadHandler = new Handler(Looper.getMainLooper());

    /**
     * 获取当前可用的线程池对象
     *
     */
    private static synchronized ThreadPoolExecutor getDefaultThreadPool() {
        if (null == sThreadPoolExecutor) {
            sThreadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool(new LinkThreadFactory());
            // 始终存在Ncpu 个线程，除非设置了允许CoreThread超时
            sThreadPoolExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
            // 设置空闲线程允许存活的时间，防止空闲线程过多导致资源浪费
            sThreadPoolExecutor.setKeepAliveTime(10L, TimeUnit.SECONDS);
        }
        return sThreadPoolExecutor;
    }

    public static Handler getMainHandler() {
        return sMainThreadHandler;
    }

    /**
     * 执行任务
     *
     */
    public static void execute(Runnable task) {
        if (null != task) {
            ThreadPoolExecutor executor = getDefaultThreadPool();
            if (null != executor) {
                try {
                    executor.execute(task);
                } catch (Exception e) {
//                    Debug.e(e);
                }
            }
        }
    }

    public static void runOnMainThread(Runnable runnable) {
        sMainThreadHandler.post(runnable);
    }

    public static void runOnMainThread(Runnable runnable, long delayMillis) {
        sMainThreadHandler.postDelayed(runnable, delayMillis);
    }


    /**
     * 移除任务
     *
     */
    public static void removeTask(Runnable task) {
        if (null != task) {
            ThreadPoolExecutor executor = getDefaultThreadPool();
            if (null != executor) {
                try {
                    executor.remove(task);
                    executor.purge();
                } catch (Exception e) {
//                    Debug.e(e);
                }
            }
        }
    }


    private static class LinkThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        LinkThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "LinkThreadPool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
