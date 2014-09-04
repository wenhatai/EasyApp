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
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

@SuppressLint("NewApi")
public class SdCacheUtil {
	private static SdCacheUtil sdCacheUtil;
	private ExecutorService mImageThreadPool = null;
	private String rootPath /*
							 * = Environment.getExternalStorageDirectory()
							 * .toString();
							 */;

	private String FileDictionary = File.separator + "QQtribleTemp";

	private String MobileRoot = null;

	private SdCacheUtil(Context context) {
		this.MobileRoot = context.getCacheDir().getPath();
		if (context.getExternalCacheDir() != null) {
			rootPath = context.getExternalCacheDir().getPath();
		}
	}

	public ExecutorService getThreadPool() {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {
					// 为了读取图片更加的流畅，我们用了3个线程来读取图片
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
				Environment.MEDIA_MOUNTED) ? rootPath + FileDictionary
				: MobileRoot + FileDictionary;
	}

	public void savaByte(final String fileName, final byte[] b)
			throws IOException {
		getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
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
				if (secondfolderFile.list() != null && secondfolderFile.list().length > 200) {
					delete(folderFile);
				}

				File f = new File(getStoragePath() + File.separator + fileName);

				try {
					OutputStream os = new FileOutputStream(f);
					os.write(b);
					os.flush();
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	public void getByteFromSDCard(final String fileName, final CallBack callBack) {
		getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File f = new File(getStoragePath() + File.separator
						+ File.separator + fileName);
				InputStream is = null;
				byte[] bt = null;
				try {
					is = new FileInputStream(f);
					bt = getBytes(is);
				} catch (Exception e) {
					e.printStackTrace();
				}
				callBack.get(bt);
			}
		});
	}

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

	public long getFileSize(String fileName) {
		return new File(getStoragePath() + File.separator + File.separator
				+ fileName).length();
	}

	public boolean isFileExists(String fileName) {
		return new File(getStoragePath() + File.separator + File.separator
				+ fileName).exists();
	}

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

	public interface CallBack {
		public void get(byte[] bt);

		public void getSize(String size);

		public void clear(String status);
	}

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
	public void clearSDCache(final CallBack callBack) {
		getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file = new File(getStoragePath());
				delete(file);
				callBack.clear("cleared");
			}
		});

	}

	/**
	 * 获取缓存大小
	 * 
	 * @return 缓存
	 */
	public void getImageCacheSize(final CallBack callBack) {
		getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file = new File(getStoragePath());
				callBack.getSize(getAutoFileOrFilesSize(getStoragePath()));
			}
		});

	}

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
}
