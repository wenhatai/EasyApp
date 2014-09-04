package com.atm.customimageview.shape;

import android.graphics.Bitmap;
/**
 *created by limingzhang on 2014/8/5
 *
 */
public  abstract class Shape {
    /**
     * 根据传人的宽高来生成图片。
     * @param first
     * @param second
     */
	 
     public abstract Bitmap getBitmap(int first,int second);
     /**
      * 根据传人的图片来生成图片。
      * @param bitmap
      */
     public abstract Bitmap getBitmap(Bitmap bitmap);
     public abstract void    setLightness(float lightness);
}
