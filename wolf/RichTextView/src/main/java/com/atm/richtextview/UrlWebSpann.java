package com.atm.richtextview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ClickableSpan;
import android.view.View;
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
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(mUrlSpannData.getUrl());
        intent.setData(content_url);
        Context context = widget.getContext();
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
