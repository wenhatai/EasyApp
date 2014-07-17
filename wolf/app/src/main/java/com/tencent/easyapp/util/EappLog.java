package com.tencent.easyapp.util;

import android.text.TextUtils;
import android.util.Log;

import com.tencent.easyapp.BuildConfig;

/**
 * Created by parrzhang on 2014/7/17.
 */
public class EappLog {
    //调试模式输出log，发布版本不输出
    public static boolean debug= BuildConfig.DEBUG;
    private static String mTag;

    public <T> EappLog(Class<T> class1)
    {
        mTag=class1.getCanonicalName();
    }

    public static void d(String msg)
    {
        if(!debug || TextUtils.isEmpty(msg))
            return;
        Log.d(mTag, getLogMsg(msg));
    }

    public static void i(String msg)
    {
        if(!debug || TextUtils.isEmpty(msg))
            return;
        Log.i(mTag, getLogMsg(msg));
    }

    public static void w(String msg)
    {
        if(!debug || TextUtils.isEmpty(msg))
            return;
        Log.w(mTag, getLogMsg(msg));
    }

    public static void e(String msg)
    {
        if(!debug || TextUtils.isEmpty(msg))
            return;
        Log.e(mTag, getLogMsg(msg));
    }

    public static void e(Throwable t) {
        if (!debug || TextUtils.isEmpty(t.getMessage())) {
            return;
        }
        Log.e(mTag,getLogMsg(t.getMessage()));
    }

    public static void e(String msg, Throwable t) {
        if (!debug || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(mTag,getLogMsg(msg),t);
    }

    private static String getLogMsg(String msg){
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        return "[" + element.getFileName() + "]["
                + element.getMethodName() + "] "
                + msg;
    }
}
