package com.linkfeeling.android.art.board.data;

import com.link.feeling.framework.component.net.NetResult;
import com.linkfeeling.android.art.board.data.bean.HomeRemoteBean;
import com.linkfeeling.android.art.board.data.bean.HomeRequest;
import com.linkfeeling.android.art.board.data.network.LinkApi;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created on 2019/1/17  11:28
 * chenpan pan.chen@linkfeeling.cn
 */
public final class LinkRemoteDataSources implements LinkDataSources {

    private final LinkApi mApi;

    LinkRemoteDataSources(Retrofit retrofit) {
        mApi = retrofit.create(LinkApi.class);
    }

    @Override
    public Single<NetResult<HomeRemoteBean>> home(HomeRequest request) {
        return mApi.home(request);
    }
}

