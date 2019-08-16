package com.linkfeeling.android.art.board.ui.home;

import com.link.feeling.framework.base.BaseMvpPresenter;
import com.link.feeling.framework.base.BaseMvpView;
import com.linkfeeling.android.art.board.data.bean.home.HomeRemoteModule;

import java.util.List;

/**
 * Created on 2019/5/14  10:53
 * chenpan pan.chen@linkfeeling.cn
 */
public interface HomeContract {

    interface View extends BaseMvpView {
        void loading(List<HomeRemoteModule> modules);

        void loadingRank(List<HomeRemoteModule> modules, String total_calorie);
    }

    interface Presenter extends BaseMvpPresenter<View> {
        void request(String gymId);

        void interval();
    }
}
