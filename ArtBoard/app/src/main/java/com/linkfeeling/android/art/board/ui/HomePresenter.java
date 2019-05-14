package com.linkfeeling.android.art.board.ui;

import com.alibaba.fastjson.JSON;
import com.link.feeling.framework.base.BasePresenter;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteModule;

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
//        LinkDataRepositories.getInstance()
//                .home(new HomeRequest())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSingleObserver<HomeRemoteBean>(this) {
//                    @Override
//                    public void onSuccess(HomeRemoteBean module) {
//                        super.onSuccess(module);
//                        mModules.clear();
//                        mModules.addAll(module.getGym_data());
//                        onceViewAttached(view -> {
//                            view.loading(mModules);
//                        });
//                    }
//                });
//        mModules.clear();
        mModules.addAll(JSON.parseArray(json , HomeRemoteModule.class));
        onceViewAttached(view -> {
            view.loading(mModules);
        });
    }

    @Override
    public void interval() {
        mDisposable = Flowable
                .interval(10, 10, TimeUnit.SECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> onceViewAttached(view -> {
                    request();
                }));
        addDisposable(mDisposable);
    }


    public static String json = "[{\n" +
            "                    \"kc\": \"1240\",\n" +
            "                    \"heart_rate\":\"130\",\n" +
            "                    \"uid\": \"xxxxx\",\n" +
            "                    \"user_name\":\"Evanzhang\",\n" +
            "                    \"head_icon\":\"https:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/DYAIOgq83eplgxNfNiaVRh78WetPoZE1OyhMGiby9eeUgUJicafAywSBBoWlvRhcSko0jrUc3ZBuIbWOOQlC5LJqA\\/132\",\n" +
            "                    \"result\":\"1\"\n" +
            "                }]";

}
