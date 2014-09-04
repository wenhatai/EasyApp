package com.atm.customimageview.tool;

import android.support.v4.util.LruCache;
/**
 * 
 * @author limingzhang
 * create on 2014/8/14
 */
@SuppressWarnings("unused")
public class ImageCache {
    private LruCache<String,byte[]> mMemoryCache;
	private static ImageCache imageCache = null;
	private ImageCache(){
    	final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory /8;
		mMemoryCache = new LruCache<String, byte[]>(cacheSize) {
			@Override
			protected int sizeOf(String key, byte[] bt) {
				return bt.length/1024;
			}
		};
    }
	public static ImageCache getInstance(){
		
		if(imageCache==null){
			imageCache = new ImageCache();
		}
		return imageCache;
	}
	public byte[] getCacheByte(String key){
    	return mMemoryCache.get(key);
    }
	public void addBitmapToMemoryCache(String key, byte bt[]) {  
	    if (getBitmapFromMemCache(key) == null && bt != null) {  
	        mMemoryCache.put(key, bt);  
	    }  
	} 
	public byte[] getBitmapFromMemCache(String key) {  
	    return mMemoryCache.get(key);  
	} 
	
}
