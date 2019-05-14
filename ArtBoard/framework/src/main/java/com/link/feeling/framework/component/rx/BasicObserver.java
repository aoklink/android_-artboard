package com.link.feeling.framework.component.rx;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.link.feeling.framework.base.BasePresenter;
import com.link.feeling.framework.component.net.config.HttpErrorHandler;
import com.link.feeling.framework.component.net.exception.NetException;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2019/1/17  11:54
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("all")
abstract class BasicObserver {

    private static final AtomicBoolean sLoadingStatus = new AtomicBoolean(false);
    protected final MainHandler mMainHandler = new MainHandler(Looper.getMainLooper());
    private final BasePresenter mPresenter;
    // loading 显示延时时间
    private final static long LOADING_VISIBLE_DELAY_MILLIS = 50;
    // loading 消失延时时间
    private final static long LOADING_GONE_DELAY_MILLIS = 300;
    // 是否需要显示 loading
    private static final int WHAT_LOADING = 123;
    // 是否需要显示 loading
    private static final int GONE_LOADING = 321;

    BasicObserver(BasePresenter presenter) {
        mPresenter = presenter;
    }

    public void onSubscribe(Disposable d) {
        mPresenter.addDisposable(d);
        mMainHandler.removeMessages(WHAT_LOADING);
        if (mPresenter.isShowLoading()) {
            sLoadingStatus.set(true);
            mMainHandler.sendMessageDelayed(mMainHandler.obtainMessage(WHAT_LOADING, mPresenter), LOADING_VISIBLE_DELAY_MILLIS);
        }
    }

    public void onError(final Throwable e) {
        hideLoading();
        e.printStackTrace();
        if (e instanceof NetException) {
            // 业务异常
            NetException apiException = (NetException) e;
            try {
                HttpErrorHandler.handleException(apiException, new HttpErrorHandler.ErrorCallbackAdapter() {

                    @Override
                    public void otherError(@NonNull NetException exception, String code, @NonNull String message) {
                        super.otherError(exception, code, message);
                        if (handleOtherError(exception, code, message)) {
                            return;
                        }
//                        mPresenter.showErrorStatus(message);
                    }
                });
            } catch (Exception e1) {
                e1.printStackTrace();
//                mPresenter.showToast(apiException.getMessage());
            }
        } else {
//            mPresenter.showToast(DisplayUtils.getString(R.string.framework_error_get));
        }
    }


    public void onComplete() {
        hideLoading();
    }

    protected boolean handleOtherError(NetException exception, String code, String message) {
        return false;
    }

    private void hideLoading() {
        sLoadingStatus.set(false);
        mMainHandler.sendMessageDelayed(mMainHandler.obtainMessage(GONE_LOADING, mPresenter), LOADING_GONE_DELAY_MILLIS);
    }

    protected static class MainHandler extends Handler {

        MainHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

}

