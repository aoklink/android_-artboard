package com.linkfeeling.android.art.board.data;

import com.link.feeling.framework.component.net.FrameworkNet;

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


}
