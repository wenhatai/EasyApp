package com.atm.customimageview.tool;

import android.graphics.Bitmap;
/**
 * 
 * @author limingzhang
 * create on 2014/9/17
 */
public class ImageInfo {
	/**
	 * 图片数组，可能是GIF，也可能是静态图
	 */
	private Bitmap[] bitmaps;
	/**
	 * 设置图片类型
	 */
	private String imageType;
	/**
	 * 保存每帧图片的延迟时间
	 */
    private long delays[];
	public Bitmap[] getBitmaps() {
		return bitmaps;
	}

	public void setBitmaps(Bitmap[] bitmaps) {
		this.bitmaps = bitmaps;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
    public long[] getDelay() {
		return delays;
	}

	public void setDelays(long[] delays) {
		this.delays = delays;
	}
	public long getDelay(int i){
		return delays[i];
	}
    public Bitmap getBitmap(int i){
    	return bitmaps[i];
    }
    /**
     * 获取图片数组所占内存大小，单位为Kb
     * @return
     */
	public int  getSize(){
    	int size = 0;
    	for(Bitmap bitmap : bitmaps){
    		size = size + bitmap.getRowBytes() * bitmap.getHeight() / 1024;
    	}
    	return size;
    }
    /**
     * 获取帧数
     * @return
     */
	public int getFrameCount() {
		return bitmaps.length;
	}
	
}
