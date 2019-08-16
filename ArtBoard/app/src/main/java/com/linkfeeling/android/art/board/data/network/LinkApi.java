package com.linkfeeling.android.art.board.data.network;

import com.link.feeling.framework.component.net.NetResult;
import com.linkfeeling.android.art.board.data.bean.home.HomeRemoteBean;
import com.linkfeeling.android.art.board.data.bean.home.HomeRequest;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created on 2019/1/15  14:37
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public interface LinkApi {

    ///////////////////////////////////////////////////////////////////////////
    ////// API ////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取验证码
     *
     * @param request
     * @return data
     */
    @POST("gym/artboard/data")
    Single<NetResult<HomeRemoteBean>> home(@Body HomeRequest request);


}
