package com.tencent.easyapp.module.spann;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by zhangpengyu on 14-8-14.
 * 通过指定的url标示指定activity进行跳转
 */
public class UrlLocalSpan extends ClickableSpan implements ParcelableSpan {
    private UrlSpannData mUrlSpannData;

    public UrlLocalSpan(UrlSpannData urlSpannData){
        this.mUrlSpannData = urlSpannData;
    }

    /**
     * 你必须根据指定的url与activity之间对应关系执行相应跳转
     * @param widget
     */
    @Override
    public void onClick(View widget) {

    }

    @Override
    public int getSpanTypeId() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrlSpannData.getContent());
    }
}
