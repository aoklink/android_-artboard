package com.linkfeeling.android.art.board.data.bean.home;

/**
 * Created on 2019/5/14  19:50
 * chenpan pan.chen@linkfeeling.cn
 */
public final class HomePartModule {

    private String name;
    private String value;

    public HomePartModule(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
