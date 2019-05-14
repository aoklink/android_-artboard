package com.linkfeeling.android.art.board.data;

import com.link.feeling.framework.component.net.FrameworkNet;
import com.linkfeeling.android.art.board.data.bean.HomeRequest;

import io.reactivex.Single;

import static com.link.feeling.framework.component.net.json.ResponseParse.mapFun;

/**
 * Created on 2019/1/17  11:24
 * chenpan pan.chen@linkfeeling.cn
 */
public final class LinkDataRepositories {

    private final static LinkDataRepositories INSTANCE = new LinkDataRepositories();

    private final LinkDataSources mRemoteDataSources;

    private LinkDataRepositories() {
        mRemoteDataSources = new LinkRemoteDataSources(FrameworkNet.getInstance().providerRetrofit());
    }

    public static LinkDataRepositories getInstance() {
        return INSTANCE;
    }




    /**
     * 注册
     *
     * @param request request
     * @return data
     */
    public Single home(HomeRequest request) {
        return mRemoteDataSources.home(request).map(mapFun());
    }


}
