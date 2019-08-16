package com.linkfeeling.android.art.board.ui.home;

import com.link.feeling.framework.base.BasePresenter;
import com.link.feeling.framework.component.rx.BaseSingleObserver;
import com.link.feeling.framework.utils.data.CollectionsUtil;
import com.link.feeling.framework.utils.data.DeviceUtils;
import com.linkfeeling.android.art.board.data.LinkDataRepositories;
import com.linkfeeling.android.art.board.data.bean.home.HomeRemoteBean;
import com.linkfeeling.android.art.board.data.bean.home.HomeRemoteModule;
import com.linkfeeling.android.art.board.data.bean.home.HomeRequest;
import com.linkfeeling.android.art.board.data.bean.home.OffsetModule;

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

    private String mMac = "WifiMac:" + DeviceUtils.getMac() + " IMEI:" + DeviceUtils.getIMEL() + " AndroidId:" + DeviceUtils.getAndroidId() + " BTMac:" + DeviceUtils.getBtMac()+" SN:"+DeviceUtils.getSN();

    // 注册监听
    private Disposable mDisposable;

    private int mTempSize;

    private String mGymId;

    @SuppressWarnings("unchecked")
    @Override
    public void request(String gymId) {
        mGymId =gymId;
        mIsLoading = false;
        LinkDataRepositories.getInstance()
                .home(new HomeRequest(mMac ,mGymId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSingleObserver<HomeRemoteBean>(this) {
                    @Override
                    public void onSuccess(HomeRemoteBean module) {
                        super.onSuccess(module);

                        if (module == null || CollectionsUtil.isEmpty(module.getGym_data())) {
                            onceViewAttached(view -> {
                                mModules.clear();
                                view.loading(mModules);
                            });
                            return;
                        }

                        if (mModules.equals(module.getGym_data())) {
                            return;
                        }

                        mModules.clear();
                        mModules.addAll(module.getGym_data());
                        if (module.isFlag()) {
                            if (CollectionsUtil.size(mModules) > CollectionsUtil.size(HomeActivity.sOffsetCache)) {
                                mTempSize = CollectionsUtil.size(mModules) - CollectionsUtil.size(HomeActivity.sOffsetCache);
                                for (int i = 0; i < mTempSize; i++) {
                                    HomeActivity.sOffsetCache.add(new OffsetModule());
                                }
                            }
                            onceViewAttached(view -> {
                                view.loading(mModules);
                            });
                        } else {
                            onceViewAttached(view -> {
                                view.loadingRank(mModules, module.getTotal_calorie());
                            });
                        }
                    }
                });
    }

    @Override
    public void interval() {
        mDisposable = Flowable
                .interval(1, 1200, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(view -> {
                    request(mGymId);
                }));
        addDisposable(mDisposable);
    }

}
