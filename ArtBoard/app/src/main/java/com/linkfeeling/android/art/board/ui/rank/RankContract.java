package com.linkfeeling.android.art.board.ui.rank;

import com.link.feeling.framework.base.BaseMvpPresenter;
import com.link.feeling.framework.base.BaseMvpView;

/**
 * Created on 2019/9/26  10:03
 * chenpan pan.chen@linkfeeling.cn
 */
public interface RankContract {

    interface View extends BaseMvpView {

        void live();
    }

    interface Presenter extends BaseMvpPresenter<View> {
        void interval();
    }

}
