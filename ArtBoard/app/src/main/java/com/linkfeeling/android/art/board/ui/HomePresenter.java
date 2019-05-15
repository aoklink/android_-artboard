package com.linkfeeling.android.art.board.ui;

import com.link.feeling.framework.base.BasePresenter;
import com.link.feeling.framework.component.rx.BaseSingleObserver;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.linkfeeling.android.art.board.data.LinkDataRepositories;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteBean;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;
import com.linkfeeling.android.art.board.data.bean.HomeRequest;
import com.linkfeeling.android.art.board.data.bean.OffsetModule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2019/5/14  10:55
 * chenpan pan.chen@linkfeeling.cn
 */
public final class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private List<HomeRemoteModule> mModules = new ArrayList<>();

    // 注册监听
    private Disposable mDisposable;

    @SuppressWarnings("unchecked")
    @Override
    public void request() {
        mIsLoading = false;
        LinkDataRepositories.getInstance()
                .home(new HomeRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSingleObserver<HomeRemoteBean>(this) {
                    @Override
                    public void onSuccess(HomeRemoteBean module) {
                        super.onSuccess(module);
                        if (module == null || CollectionsUtil.isEmpty(module.getGym_data())) {
                            return;
                        }
                        mModules.clear();
                        mModules.addAll(module.getGym_data());

                        if (CollectionsUtil.size(mModules) > CollectionsUtil.size(HomeActivity.sOffsetCache)) {
                            for (int i = 0; i < CollectionsUtil.size(mModules) - CollectionsUtil.size(HomeActivity.sOffsetCache); i++) {
                                HomeActivity.sOffsetCache.add(new OffsetModule());
                            }
                        }
                        onceViewAttached(view -> {
                            view.loading(mModules);
                        });
                    }
                });
    }

    @Override
    public void interval() {
        mDisposable = Flowable
                .interval(1, 2, TimeUnit.SECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(view -> {
                    request();
                }));
        addDisposable(mDisposable);
    }

}
