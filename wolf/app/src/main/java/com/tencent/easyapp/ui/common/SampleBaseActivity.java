package com.tencent.easyapp.ui.common;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.easyapp.EappData;
import com.tencent.easyapp.constant.EappConstant;
import com.tencent.tauth.Tencent;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleBaseActivity extends Activity {
    private static Tencent mTencent;

    public static Tencent getTencent() {
        if(mTencent == null){
            mTencent = Tencent.createInstance(EappConstant.QQApiID, EappData.getContext());
        }
        return mTencent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
