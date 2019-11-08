package com.linkfeeling.android.art.board.ui.rank;

import com.link.feeling.framework.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2019/9/26  10:05
 * chenpan pan.chen@linkfeeling.cn
 */
public final class RankPresenter extends BasePresenter<RankContract.View> implements RankContract.Presenter {

    private Disposable mDisposable1;
    private Disposable mDisposable;


    private Disposable mDisposableTimer;
    private Disposable mDisposablePage;


    @Override
    public void interval() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        mDisposable = Flowable
                .interval(0, 5, TimeUnit.MINUTES)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(RankContract.View::live));
        addDisposable(mDisposable);
    }

    @Override
    public void count() {
        if (mDisposableTimer != null && !mDisposableTimer.isDisposed()) {
            mDisposableTimer.dispose();
            mDisposableTimer = null;
        }
        mDisposableTimer = Flowable
                .interval(0, 1, TimeUnit.SECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(RankContract.View::timer));
        addDisposable(mDisposableTimer);
    }

    @Override
    public void countPage() {
        if (mDisposablePage != null && !mDisposablePage.isDisposed()) {
            mDisposablePage.dispose();
            mDisposablePage = null;
        }
        mDisposablePage = Flowable
                .interval(60, 60, TimeUnit.SECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(RankContract.View::scrollPage));
        addDisposable(mDisposablePage);
    }

    @Override
    public void countRank() {
        if (mDisposable1 != null && !mDisposable1.isDisposed()) {
            mDisposable1.dispose();
            mDisposable1 = null;
        }
        mDisposable1 = Flowable
                .interval(0, 15, TimeUnit.SECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(RankContract.View::scrollRank));
        addDisposable(mDisposable1);
    }
}
