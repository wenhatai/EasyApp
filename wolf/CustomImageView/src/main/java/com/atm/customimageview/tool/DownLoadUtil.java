package com.atm.customimageview.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.IntentSender.SendIntentException;
import android.os.Handler;
import android.os.Message;

/**
 * created by limingzhang on 2014/8/5
 *
 */
public class DownLoadUtil {
	private ExecutorService mImageThreadPool = null;
	private static DownLoadUtil downLoadUtil;
   
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

	public static DownLoadUtil getInstance() {
		if (downLoadUtil == null) {
			downLoadUtil = new DownLoadUtil();
		}
		return downLoadUtil;
	}

	/**
	 * 下载图片
	 * @param urlPath
	 *            网络图片的URL。
	 * @param filePath
	 *            保存的sdcard的路径
	 * @param callBack
	 *            网络请求成功之后的回调
	 */
	public void downloadFile(final String urlPath, final String filePath,
			final CallBack callBack) {
		getThreadPool().execute(new Runnable() {
			String url = urlPath;
			HttpURLConnection conn = null;
			byte[] bt = null;

			@Override
			public void run() {
				try {
					URL imageUrl = null;
					InputStream is = null;
					imageUrl = new URL(urlPath);
					if (conn == null) {
						conn = (HttpURLConnection) imageUrl.openConnection();
						conn.setRequestMethod("GET");
						conn.addRequestProperty("Referer",
								"http://xiaoqu.qq.com/mobile/index.html");
						conn.setReadTimeout(5000);
						conn.connect();
					}
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
				if(bt!=null){
				callBack.downLoadByte(bt, url);
				}
			}
		});

	}
   /**
    * 通过文件输入流，获取字节数组
    * @param is
    * @return
    * @throws java.io.IOException
    */
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
    /**
     * 设置回调
     * @author limitvic
     *
     */
	public interface CallBack {
		public void downLoadByte(byte[] bt, String url);
	}
}
