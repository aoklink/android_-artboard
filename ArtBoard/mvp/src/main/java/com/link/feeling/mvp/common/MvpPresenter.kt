package com.link.feeling.mvp.common

import androidx.annotation.NonNull
import androidx.annotation.UiThread

/**
 * Created on 2019/1/11  15:19
 * chenpan pan.chen@linkfeeling.cn
 */
interface MvpPresenter <V : MvpView> {

    @UiThread
    fun attachView(@NonNull view: V)

    @UiThread
    fun detachView()

    @UiThread
    fun destroy()

}