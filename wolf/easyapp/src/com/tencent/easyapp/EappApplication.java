package com.tencent.easyapp;

import android.app.Application;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class EappApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EappData.init(this.getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCache(new WeakMemoryCache()).build();
        ImageLoader.getInstance().init(config);
    }
}
