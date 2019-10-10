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


    // 注册监听
    private Disposable mDisposable;

    @Override
    public void interval() {
        mDisposable = Flowable
                .interval(0, 5, TimeUnit.MINUTES)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(RankContract.View::live));
        addDisposable(mDisposable);
    }
}
