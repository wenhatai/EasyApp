package com.atm.richtextview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by zhangpengyu on 14-8-14.
 */
public class ImageSpannData extends BaseSpannData {
    private String imagePath;
    private int imageRes;
    private int imageHeight;
    private int imageWidth;

    public ImageSpannData(String content) {
        super(content);
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Bitmap getBitmap(){
        Bitmap bitmap = null;
        //从网络上拉取图片
        if(imagePath!=null){

        }else if(imageRes!=0){
            bitmap = BitmapFactory.decodeResource(EappData.getResources(),imageRes);
            if(imageWidth!=0&&imageHeight!=0){
                bitmap = Bitmap.createScaledBitmap(bitmap,imageWidth,imageHeight,true);
            }
        }
        return bitmap;
    }
}
