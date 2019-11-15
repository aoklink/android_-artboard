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

    @Override
    public void interval() {
        // 注册监听
        Disposable mDisposable = Flowable
                .interval(30, 30, TimeUnit.SECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(HomeContract.View::loading));
        addDisposable(mDisposable);
    }

    @Override
    public void count() {
        Disposable mDisposable1 = Flowable
                .interval(0, 500, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(HomeContract.View::timer));
        addDisposable(mDisposable1);
    }

}
