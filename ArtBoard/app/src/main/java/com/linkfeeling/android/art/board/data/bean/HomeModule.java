package com.linkfeeling.android.art.board.data.bean;

import java.util.List;

/**
 * Created on 2019/5/14  11:36
 * chenpan pan.chen@linkfeeling.cn
 */
@SuppressWarnings("unused")
public final class HomeModule {

  public   static class ITEM_TYPE {
        public final static int TYPE1 = 1;
        public final static int TYPE2 = 2;
        public final static int TYPE3 = 3;
        public final static int TYPE4 = 4;
        public final static int TYPE5 = 5;
    }

    private int itemType;

    private List<HomeRemoteModule> modules;


    public HomeModule(int itemType, List<HomeRemoteModule> modules) {
        this.itemType = itemType;
        this.modules = modules;
    }

    public int getItemType() {
        return itemType;
    }

    public List<HomeRemoteModule> getModules() {
        return modules;
    }
}
