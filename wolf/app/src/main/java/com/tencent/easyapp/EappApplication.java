package com.tencent.easyapp;

import android.app.Application;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class EappApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EappData.init(this.getApplicationContext());
    }
}
