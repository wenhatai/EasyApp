package com.atm.customimageview.tool;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
/**
 * 
 * @author limingzhang create on 2014/8/14
 */
public class ImageCache {
	//LRU缓存。
	private LruCache<String, ImageInfo> mMemoryCache;
	private static ImageCache imageCache = null;

	private ImageCache() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, ImageInfo>(cacheSize) {
			@Override
			protected int sizeOf(String key, ImageInfo imageInfo) {
				return imageInfo.getSize();
			}
		};
	}

	public static ImageCache getInstance() {

		if (imageCache == null) {
			imageCache = new ImageCache();
		}
		return imageCache;
	}
    /**
     * 将ImageInfo添加到内存中去
     * @param key       
     * @param imageInfo
     */
	public void addImageInfoToMemoryCache(String key, ImageInfo imageInfo) {
		if (!isExistImageInfo(key) && imageInfo != null) {
			mMemoryCache.put(key, imageInfo);
		}
	}
	/**
	 * 通过key来获得一个ImageInfo对象
	 * @param key
	 * @return
	 */
    public ImageInfo getImageInfo(String key){
    	return mMemoryCache.get(key);
    }
    /**
     * 判断该key对应的ImageInfo图像是否存在
     * @param key
     * @return
     */
	public boolean isExistImageInfo(String key) {
		return mMemoryCache.get(key) == null ? false : true;
	}

}
