package com.tencent.easyapp.module.spann;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ClickableSpan;
import android.view.View;

import com.tencent.easyapp.ui.activity.EappWebViewActivity;

/**
 * Created by zhangpengyu on 14-8-15.
 */
public class UrlWebSpann extends ClickableSpan implements ParcelableSpan {
    private UrlSpannData mUrlSpannData;

    public UrlWebSpann(UrlSpannData urlSpannData){
        this.mUrlSpannData = urlSpannData;
    }

    @Override
    public void onClick(View widget) {
        Context context = widget.getContext();
        Intent intent = new Intent(context, EappWebViewActivity.class);
        Bundle bundle = mUrlSpannData.getBundle();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(EappWebViewActivity.Url, mUrlSpannData.getUrl());
        intent.putExtras(bundle);
        context.startActivity(intent);
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
