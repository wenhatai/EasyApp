package com.tencent.easyapp.module.spann;

import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

import com.tencent.easyapp.EappData;

/**
 * Created by zhangpengyu on 14-8-15.
 */
public class DrawableSpan extends DynamicDrawableSpan {
    private Drawable mDrawalbe;
    private int mSize;
    private int mResourceId;

    public DrawableSpan(int resourceId){
        this.mResourceId = resourceId;
    }

    public DrawableSpan(int size,int resourceId){
        this.mSize = size;
        this.mResourceId = resourceId;
    }

    @Override
    public Drawable getDrawable() {
        try{
            mDrawalbe = EappData.getResources().getDrawable(mResourceId);
            if(mSize!=0){
                int height = mSize;
                int width = mSize*mDrawalbe.getIntrinsicWidth()/mDrawalbe.getIntrinsicHeight();
                mDrawalbe.setBounds(0,0,width,height);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mDrawalbe;
    }
}
