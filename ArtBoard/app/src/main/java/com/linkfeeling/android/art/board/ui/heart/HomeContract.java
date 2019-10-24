package com.linkfeeling.android.art.board.ui.heart;

import com.link.feeling.framework.base.BaseMvpPresenter;
import com.link.feeling.framework.base.BaseMvpView;

/**
 * Created on 2019/5/14  10:53
 * chenpan pan.chen@linkfeeling.cn
 */
public interface HomeContract {

    interface View extends BaseMvpView {
        void loading();
        void timer();
    }

    interface Presenter extends BaseMvpPresenter<View> {
        void interval();
        void count();
    }
}
