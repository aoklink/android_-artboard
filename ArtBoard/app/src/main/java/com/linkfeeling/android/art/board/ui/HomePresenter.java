package com.linkfeeling.android.art.board.ui;

import com.link.feeling.framework.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2019/5/14  10:55
 * chenpan pan.chen@linkfeeling.cn
 */
public final class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    // 注册监听
    private Disposable mDisposable;
    private Disposable mDisposable1;

    @Override
    public void interval() {
        mDisposable = Flowable
                .interval(2, 2, TimeUnit.MINUTES)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(HomeContract.View::loading));
        addDisposable(mDisposable);
    }

    @Override
    public void count() {
        mDisposable1 = Flowable
                .interval(0, 1, TimeUnit.SECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(HomeContract.View::timer));
        addDisposable(mDisposable1);
    }

}
