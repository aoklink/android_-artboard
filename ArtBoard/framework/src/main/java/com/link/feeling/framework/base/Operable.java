package com.link.feeling.framework.base;

import android.content.Context;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2019/1/14  16:31
 * chenpan pan.chen@linkfeeling.cn
 */
public interface Operable {

    /**
     * 将 disposable 添加到 Presenter 统一管理
     *
     * @param disposable disposable
     */
    void addDisposable(@NonNull Disposable disposable);

    /**
     * 显示 toast
     *
     * @param message 内容
     */
    void showToast(String message);

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    Context getContext();

}

