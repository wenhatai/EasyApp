package com.tencent.easyapp;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by parrzhang on 2014/7/16.
 * 可以获取context，resource，color等
 */
public class EappData {
    private static Context sContext;

    public static void init(Context context){
        if(sContext == null){
            sContext = context.getApplicationContext();
        }
    }

    public static Context getContext(){
        return sContext;
    }

    public static Resources getResources(){
        return sContext.getResources();
    }

    public static int getColor(int colorRes){
        return sContext.getResources().getColor(colorRes);
    }

    public static float getDimens(int dimenRes){
        return sContext.getResources().getDimension(dimenRes);
    }
}
