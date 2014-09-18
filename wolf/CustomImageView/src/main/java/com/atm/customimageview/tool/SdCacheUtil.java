package com.atm.customimageview.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class SdCacheUtil {
	private static SdCacheUtil sdCacheUtil;
	private ExecutorService mImageThreadPool = null;
	private String rootPath ;
	private String fileDictionary = File.separator + "QQtribleTemp";
	private String mobileRoot = null;

	private SdCacheUtil(Context context) {
		this.mobileRoot = context.getCacheDir().getPath();
		if (context.getExternalCacheDir() != null) {
			rootPath = context.getExternalCacheDir().getPath();
		}
	}

	public ExecutorService getThreadPool() {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {
					mImageThreadPool = Executors.newFixedThreadPool(3);
				}
			}
		}
		return mImageThreadPool;
	}

	public static SdCacheUtil getInstance(Context context) {
		if (sdCacheUtil == null) {
			sdCacheUtil = new SdCacheUtil(context);
		}
		return sdCacheUtil;
	}

	private String getStoragePath() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? rootPath + fileDictionary
				: mobileRoot + fileDictionary;
	}
   /**
    * 将字符数组保存到SD卡
    * @param fileName 文件名
    * @param b        内容
    * @throws java.io.IOException
    */
	public void savaByte(final String fileName, final byte[] b)
			throws IOException {
		getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				if (b == null) {
					return;
				}
				File folderFile = new File(getStoragePath());
				if (!folderFile.exists()) {
					folderFile.mkdir();
				}
				File secondfolderFile = new File(getStoragePath());
				if (!secondfolderFile.exists()) {
					secondfolderFile.mkdir();
				}

				//添加判空处理  freedeng
				if (secondfolderFile.list() != null && secondfolderFile.list().length > 1000) {
					delete(folderFile);
				}

				File f = new File(getStoragePath() + File.separator + fileName);

				try {
					OutputStream os = new FileOutputStream(f);
					os.write(b);
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}
    /**
     * 通过文件名获取字符数组，并通过回调返回。
     * @param fileName
     * @param imageCallBack
     */
	public void getByteFromSDCard(final String fileName, final ImageCallBack imageCallBack) {
		getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				File f = new File(getStoragePath()
						+ File.separator + fileName);
				InputStream is = null;
				byte[] bt = null;
				try {
					is = new FileInputStream(f);
					bt = getBytes(is);
				} catch (Exception e) {
					e.printStackTrace();
				}
				imageCallBack.get(bt,fileName);
			}
		});
	}
   /**
    * 通过输入流来获得字节数组
    * @param is
    * @return
    * @throws java.io.IOException
    */
	public byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
    /**
     * 获取文件大小
     * @param fileName
     * @return
     */
	public long getFileSize(String fileName) {
		return new File(getStoragePath() + File.separator 
				+ fileName).length();
	}
    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
	public boolean isFileExists(String fileName) {
		return new File(getStoragePath() + File.separator
				+ fileName).exists();
	}
    /**
     * 删除缓存文件夹
     */
	public void deleteFile() {
		File dirFile = new File(getStoragePath());
		if (!dirFile.exists()) {
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}
		dirFile.delete();
	}
	/**
	 * 删除文件
	 * @param file
	 */
	private void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}
			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

	/**
	 * 清除缓存
	 */
	public void clearSDCache(final ClearCacheCallBack clearCacheCallBack) {
		getThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				File file = new File(getStoragePath());
				delete(file);
				clearCacheCallBack.clear("cleared");
			}
		});

	}

	/**
	 * 获取缓存大小
	 */
	public void getImageCacheSize(final GetCacheSizeCallBack getCacheSizeCallBack) {
		getThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				getCacheSizeCallBack.getSize(getAutoFileOrFilesSize(getStoragePath()));
			}
		});

	}
    /**
     * 获取文件总大小
     */
	public String getAutoFileOrFilesSize(String filePath) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("获取文件大小", "获取失败!");
		}
		return FormetFileSize(blockSize);
	}
   /**
    * 获取文件夹大小
    * @param f
    * @return
    * @throws Exception
    */
	private long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}
    /**
     * 获取文件大小
     * @param file
     * @return
     * @throws Exception
     */
	@SuppressWarnings("resource")
	private long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}
    /**
     * 给文件大小添加单位
     * @param fileS
     * @return
     */
	private String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}
    /**
     * 
     * @author limingzhang
     *
     */
	public interface ImageCallBack {
		public void get(byte[] bt, String url);
	}
    public interface GetCacheSizeCallBack{
    	public void getSize(String size);
    }
    public interface ClearCacheCallBack{
    	public void clear(String status);
    }
}
