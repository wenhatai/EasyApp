package com.atm.richtextview;

import android.os.Bundle;

/**
 * Created by zhangpengyu on 14-8-14.
 * 要跳转的Activity。定义要跳转的url，需要携带的参数。以及跳转模式
 */
public class UrlSpannData extends BaseSpannData {
    /**
     * 应用Activity跳转
     */
    public static final int LocalFlag = 1;
    /**
     * 应用WebView跳转
     */
    public static final int WebViewFlag = 2;
    /**
     * 用浏览器跳转
     */
    public static final int BrowserFlag = 3;

    //要跳转的url
    private String url;
    //跳转模式
    private int flag;
    //跳转的参数
    private Bundle bundle;

    public UrlSpannData(String content) {
        super(content);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
