package com.link.feeling.framework.base;

import android.content.Context;

import com.link.feeling.mvp.MvpBasePresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2019/1/14  16:18
 * chenpan pan.chen@linkfeeling.cn
 */
public class BasePresenter<V extends BaseMvpView> extends MvpBasePresenter<V> implements Operable {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private boolean mInitSuccess = false;

    public boolean mIsLoading = true;

    public void initSuccess() {
        mInitSuccess = true;
        runQueuedActions();
    }

    public boolean isShowLoading() {
        return mIsLoading;
    }

    /**
     * 添加 Disposable 统一管理
     *
     * @param disposable disposable
     */
    @Override
    public final void addDisposable(@NonNull Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void showToast(final String message) {
        onceViewAttached(view -> {
            view.showToast(message);
        });
    }

    @Override
    public Context getContext() {
        if (getViewRef() != null && getViewRef().get() != null) {
            return getViewRef().get().getContext();
        }
        return null;
    }


    /**
     * 清除所有的 Disposable
     */
    protected final void clearDisposables() {
        mCompositeDisposable.clear();
    }

    @Override
    public void destroy() {
        super.destroy();
        mCompositeDisposable.dispose();
    }

    @Override
    protected boolean canExecute() {
        return mInitSuccess;
    }
}

