package com.atm.customimageview.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *created by limingzhang on 2014/8/5
 *
 */
public class DownLoadUtil {
	private ExecutorService mImageThreadPool = null;
	private static DownLoadUtil downLoadUtil;
	public ExecutorService getThreadPool() {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {
					// 为了下载图片更加的流畅，我们用了3个线程来下载图片
					mImageThreadPool = Executors.newFixedThreadPool(3);
				}
			}
		}
		return mImageThreadPool;
	}
	public static DownLoadUtil getInstance(){
		if(downLoadUtil==null){
			downLoadUtil = new DownLoadUtil();
		}
		return downLoadUtil;
	}
	/**
	 * 
	 * @param urlPath 网络图片的URL。
	 * @param filePath 保存的sdcard的路径
	 * @param callBack 网络请求成功之后的回调
	 */
	public void downloadFile(final String urlPath, final String filePath,final CallBack callBack) {
		getThreadPool().execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				URL imageUrl = null;
				InputStream is = null;
				byte[] bt = null;
				try {
					imageUrl = new URL(urlPath);

					HttpURLConnection conn = (HttpURLConnection) imageUrl
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setReadTimeout(5000);
					conn.connect();
					is = conn.getInputStream();
					bt = getBytes(is);
					if (filePath != null || "".equals(filePath)) {
						File f = new File(filePath);
						if (!f.exists()) {
							f.createNewFile();
						}
						FileOutputStream fOut = new FileOutputStream(f);
						fOut.write(bt);
						fOut.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				callBack.downLoadByte(bt);
			}
		});

	}
   
	private static byte[] getBytes(InputStream is) throws IOException {
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

	public static byte[] getImageFromSDCard(String filePath) {
		File f = new File(filePath);
		InputStream is = null;
		byte[] bt = null;
		try {
			is = new FileInputStream(f);
			bt = getBytes(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bt;
	}
	public interface CallBack{
		public void downLoadByte(byte[] bt);
	}
}
