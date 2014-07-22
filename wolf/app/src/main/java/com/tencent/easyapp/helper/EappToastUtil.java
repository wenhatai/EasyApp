package com.tencent.easyapp.helper;

import android.widget.Toast;

import com.tencent.easyapp.EappData;

/**
 * you can custom your toast
 * Created by parrzhang on 2014/7/22.
 */
public class EappToastUtil {

    public static void easyToast(String content){
        Toast.makeText(EappData.getContext(), content, Toast.LENGTH_SHORT).show();
    }
}
