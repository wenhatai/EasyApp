package com.atm.photoselector.tool;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.atm.photoselector.bean.SQLThumbnailBean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

/**
 * 
 * created by limingzhang on 2014/8/12
 *
 */
public class ImageLoader {
	private LruCache<String, Bitmap> mMemoryCache;

	// 通过JNI来获得图片的字节数组
	public native byte[] getResultFromJni(String str);

	private PhotoSelectorDao photoSelectorDao;
	private Bitmap bitmap;
	private Matrix matrix = new Matrix();
	private ExecutorService mImageThreadPool = null;
	private static ImageLoader mInstance;
	// 准备插入数据库的缩略图路径的集合
	private List<SQLThumbnailBean> prepareInsertList = new ArrayList<SQLThumbnailBean>();
	// 判断线程池是否关闭
	private boolean isThreadShut = false;
	// 表示存储缩略图的父路径
	private String father = "";

	private ImageLoader(Context context) {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 4;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
			}
		};
     //如果有SD卡的话，就是使用SD卡缓存，否则使用内存缓存.
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			father = context.getExternalCacheDir().getPath() + "Thumbnail";
		} else {
			father = context.getCacheDir().getPath() + "Thumbnail";
		}

		File dirFile = new File(father);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		photoSelectorDao = new PhotoSelectorDao(context);
	}
   /**
    * 
    * @param context
    * @return 获得一个ImageLoader的实例
    */
	public static ImageLoader getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new ImageLoader(context);
		}
		return mInstance;
	}
   /**
    * @return 从线程池中的一个获得一个线程。
    */
	public ExecutorService getThreadPool() {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {
					mImageThreadPool = Executors.newFixedThreadPool(2);
				}
			}
		}
		return mImageThreadPool;
	}
   /**
    * 
    * @param key 图片的路径
    * @param bitmap 需要缓存进内存的位图
    * 添加进内存
    */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null && bitmap != null) {
			mMemoryCache.put(key, bitmap);
		}
	}
   /**
    * 
    * @param key 图片的路径
    * @return 返回内存中的图片
    */
	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
    
	public LruCache<String, Bitmap> getmMemoryCache() {
		return mMemoryCache;
	}
    /**
     * 
     * @param path  需要加载的图片路径
     * @param mCallBack 设置读取完图片之后的回调 
     * @return 一张缩略图
     */
	public Bitmap loadNativeImage(final String path,
			final onImageLoaderListener mCallBack) {

		Bitmap bitmap = getBitmapFromMemCache(path);
		final Handler mHander = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mCallBack.onImageLoader((Bitmap) msg.obj, path);
			}
		};

		if (bitmap == null) {
			getThreadPool().execute(new Runnable() {

				@Override
				public void run() {
					Bitmap mBitmap = null;
					File f = new File(path); // 这里是一个可以优化的地方
					String name = f.getName();
					if (!name.startsWith("Thumbnail")) {
						mBitmap = changeSrcToThumbnail(path);
					} else {
						mBitmap = getThumbnail(path);
					}
					Message msg = mHander.obtainMessage();
					msg.obj = mBitmap;
					mHander.sendMessage(msg);
					addBitmapToMemoryCache(path, mBitmap);
				}
			});
		}
		return bitmap;

	}
    /**
     * @param path
     * @return
     * 把原图转为缩略图。并保存到数据库和SD卡。 
     */
	private Bitmap changeSrcToThumbnail(final String path) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 1;
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opt);
		int bitmapSize = opt.outHeight * opt.outWidth * 4;
		opt.inSampleSize = bitmapSize / (1000 * 2000);
		opt.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, opt);
		if (bitmap == null) {
			return null;
		}
		int index = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth()
				: bitmap.getHeight();
		matrix.setScale(1, 1);

		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, index, index,
				matrix, false);
		File f = new File(path);
		File thumbf = new File(father + File.separator + "Thumbnail"
				+ f.getName());
		if (!thumbf.exists()) {
			saveThumbnailFile(bitmap2, "Thumbnail" + f.getName());
			SQLThumbnailBean stbBean = new SQLThumbnailBean();
			stbBean.setfatherFoldName(f.getParentFile().getName());
			stbBean.setThumbnailPath(father + File.separator + "Thumbnail"
					+ f.getName());
			stbBean.setSrcPaht(path);
			stbBean.setCreateTime(f.lastModified());
			if (!isThreadShut) {
				prepareInsertList.add(stbBean);
			}
		}
		return bitmap2;

	}
    /**
     * @param path
     * @return 获得一张缩略图
     */
	private Bitmap getThumbnail(final String path) {
		byte data[] = getResultFromJni(path);
		ByteArrayInputStream in = new ByteArrayInputStream(data, 0, data.length);
		return BitmapFactory.decodeStream(in);
	}
    /**
     * @param pathList
     * @return 预留的方法
     */
	public List<Bitmap> getSelectImage(List<String> pathList) {

		List<Bitmap> bitmapList = new ArrayList<Bitmap>();
		for (String path : pathList) {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inSampleSize = 1;
			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, opt);
			int bitmapSize = opt.outHeight * opt.outWidth * 4;
			opt.inSampleSize = bitmapSize / (1000 * 2000);
			opt.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(path, opt);
			int index = bitmap.getWidth() < bitmap.getHeight() ? bitmap
					.getWidth() : bitmap.getHeight();
			matrix.setScale(1, 1);
			Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, index, index,
					matrix, false);
			bitmapList.add(bitmap2);
		}
		return bitmapList;
	}
    /**
     * 
     * @param bm
     * @param name
     * 把缩略图保持到SD卡缓存
     */
	private void saveThumbnailFile(Bitmap bm, String name) {
		if (bm == null) {
			return;
		}
		String path = father + File.separator + name;
		File ThumbnailFile = new File(path);
		ThumbnailFile.deleteOnExit();
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(ThumbnailFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 取消正在加载的任务
	 */
	public synchronized void cancelTask() {
		if (mImageThreadPool != null) {
			isThreadShut = true;
			mImageThreadPool.shutdown();
			mImageThreadPool = null;
		}
	}
   /**
    * 在数据库中插入
    * @param context
    */
	public void insertPrepareData(Context context) {
		photoSelectorDao = new PhotoSelectorDao(context);
		if (prepareInsertList.size() != 0) {
			photoSelectorDao.addAll(prepareInsertList);
			prepareInsertList.clear();
		}
		isThreadShut = false;
	}
   /**
    * @author limingzhang
    */
	public interface onImageLoaderListener {
		void onImageLoader(Bitmap bitmap, String url);
	}

	public void setThreadShut(boolean isThreadShut) {
		this.isThreadShut = isThreadShut;
	}
   /**
    * 加载JNI
    */
	static {
		System.loadLibrary("ImageLoader");
	}
}
