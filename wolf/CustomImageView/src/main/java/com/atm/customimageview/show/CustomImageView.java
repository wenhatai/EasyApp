package com.atm.customimageview.show;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.atm.customimageview.shape.Circle;
import com.atm.customimageview.shape.CircleRect;
import com.atm.customimageview.shape.Shape;
import com.atm.customimageview.tool.DownLoadUtil;
import com.atm.customimageview.tool.ImageCache;
import com.atm.customimageview.tool.ImageInfo;
import com.atm.customimageview.tool.SdCacheUtil;
import com.example.customimageview.R;

/**
 * 
 * @author limingzhang
 *
 */
@SuppressLint({ "DrawAllocation", "HandlerLeak" })
public class CustomImageView extends ImageView implements GifAction {
	
	//GIF解析器
	private GifDecoder gifDecoder = null;
	//标志GIF是否会运行。
	private boolean isRun = true;
	//当前上下文
	protected Context mContext;
	//默认的形状是矩形。
	private Shape shape = new CircleRect(0);
	//默认边框粗细为0
	private int borderwidth = 0;
	//默认边框颜色为白色
	private int borderColor = Color.WHITE;
	//0表示圆形
	public final static int CIRCLE = 0;
	//1表示圆角矩形
	public final static int CIRCLECORNERRECT = 1;
	//获取内存缓存的一个单例
	private ImageCache imageCache = ImageCache.getInstance();
	//获得Sd卡缓存的一个单例
	private SdCacheUtil sdCacheUtil = SdCacheUtil.getInstance(getContext());
	//是否需要Sd卡缓存
	private boolean SDCardCache = false;
	//保存当前请求网络的URL
	private String httpPathURL = "";
	//保存当前处理后的URL
	private String subUrl = "";
	//用于边框绘制
	private Paint paint = new Paint();
	//用于保存GIF剪切后的每一帧。
	private Bitmap[] cutBitmaps;   
	//用于判断剪切后的gif每一帧和之前是否是同一张Bitmap
	private boolean ChangeBitmaps = false; 
	//获取默认图片
	private Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(),
			R.drawable.gray);

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public CustomImageView(Context context) {
		super(context);
	}
    /**
     * 设置形状
     * @param shapeindex CIRCLE表示圆形，CIRCLECORNERRECT表示圆角矩形。
     * @param outerRadiusRat  如果是圆角矩形，需要提供弧度值，如果是圆形可以写入任意值。
     */
	public void setShape(int shapeindex, int outerRadiusRat) {
		if (shapeindex == CIRCLE) {
			shape = new Circle();
		} else if (shapeindex == CIRCLECORNERRECT) {
			shape = new CircleRect(outerRadiusRat);
		}
	}
   /**
    * 判断是否设置Sd卡缓存
    * @return
    */
	private boolean isSDCardCache() {
		return SDCardCache;
	}
   /**
    * 设置Sd卡缓存
    * @param sDCardCache
    */
	public void setSDCardCache(boolean sDCardCache) {
		SDCardCache = sDCardCache;
	}
   /**
    * 设置亮度
    * @param lightness  亮度取0.0到1.0之间
    */
	public void setLightness(float lightness) {
		shape.setLightness(lightness);
	}
   /**
    * 设置GIF图像
    * @param gif
    */
	private void setGifDecoderImage(byte[] gif) {
		if (gifDecoder != null) {
			gifDecoder.free();
			gifDecoder = null;
		}
		gifDecoder = new GifDecoder(gif, this);
		gifDecoder.start();

	}
	 /**
	    * 设置GIF图像
	    */
	private void setGifDecoderImage(InputStream is) {
		if (gifDecoder != null) {
			gifDecoder.free();
			gifDecoder = null;
		}
		gifDecoder = new GifDecoder(is, this);
		//pause = false;
		gifDecoder.start();
	}
   /**
    * 判断是否为GIF
    * @param is
    * @return
    */
	private boolean isGif(InputStream is) {
		String id = "";
		try {
			for (int i = 0; i < 6; i++) {

				id += (char) is.read();
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (id.startsWith("GIF")) {
			return true;
		} else {
			return false;
		}
	}
	/**
	    * 设置GIF图像
	    */
	public void setGifImage(int resId) {
		Resources r = this.getResources();
		InputStream is = r.openRawResource(resId);
		setGifDecoderImage(is);
	}
   /**
    * 绘制图片
    */
	public Handler loadhandler = new Handler() {
		public void handleMessage(Message msg) {
			setImageBitmap((Bitmap)msg.obj);
		};
	};

   /**
    * 设置网络图片以及保存路径
    * @param httpPath 请求的图片地址
    * @param sdPath   保存的Sd卡地址
    */
	public void setHttpImage(final String httpPath, final String sdPath) {

		releaseCutBitmap();
		if (httpPath==null||httpPath.equals(httpPathURL)) {
			return;
		}
		isRun = false;
		httpPathURL = httpPath;
		setImageBitmap(shape.getBitmap(defaultBitmap));
		subUrl = httpPath.replaceAll("[^\\w]", "");
		if (!imageCache.isExistImageInfo(subUrl)) {
			if (isSDCardCache()) {
				if (!sdCacheUtil.isFileExists(subUrl)) {
					getFromNetWork(httpPath, sdPath);
				} else {
					getFromSDcard(subUrl);
				}
			} else {
				getFromNetWork(httpPath, sdPath);
			}
		} else {
			getFromMemmery(subUrl);
		}
	}
  /**
   * 从网络上获取图片
   * @param httpPath
   * @param sdPath
   */
	private void getFromNetWork(final String httpPath, final String sdPath) {
		try {
			DownLoadUtil.getInstance().downloadFile(httpPath, sdPath,
					new DownLoadUtil.CallBack() {
						@Override
						public void downLoadByte(byte[] bt, String path) {
							InputStream inputStream = new ByteArrayInputStream(
									bt);
							if (isGif(inputStream)) {
								setGifDecoderImage(bt);
							} else {
								Bitmap rawBitmap = BitmapFactory
										.decodeByteArray(bt, 0, bt.length);
								if (httpPathURL.equals(path)) {
									Message message = new Message();
									message.obj = shape.getBitmap(rawBitmap);
									loadhandler.sendMessage(message);
								}
								ImageInfo imageInfo = new ImageInfo();
								Bitmap[] bitmap = new Bitmap[1];
								bitmap[0] = rawBitmap;
								imageInfo.setBitmaps(bitmap);
								imageInfo.setImageType("DEFAULT");
								imageCache.addImageInfoToMemoryCache(subUrl,
										imageInfo);
								if (isSDCardCache()) {
									try {
										sdCacheUtil.savaByte(subUrl, bt);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}

						}
					});

		} catch (Exception e) {
		}
	}
   /**
    * 从Sd卡获取图片
    * @param subUrl
    */
	private void getFromSDcard(final String subUrl) {
		sdCacheUtil.getByteFromSDCard(subUrl,
				new SdCacheUtil.ImageCallBack() {
					@Override
					public void get(byte[] bt, String url) {
						InputStream inputStream = new ByteArrayInputStream(bt);
						if (isGif(inputStream)) {
							setGifDecoderImage(bt);
						} else {
							Bitmap rawBitmap = BitmapFactory
									.decodeByteArray(bt, 0, bt.length);
							if (subUrl.equals(url)) {
								Message message = new Message();
								message.obj = shape.getBitmap(rawBitmap);
								loadhandler.sendMessage(message);
							}
							ImageInfo imageInfo = new ImageInfo();
							Bitmap[] bitmap = new Bitmap[1];
							bitmap[0] = rawBitmap;
							imageInfo.setBitmaps(bitmap);
							imageInfo.setImageType("DEFAULT");
							imageCache.addImageInfoToMemoryCache(subUrl,
									imageInfo);
						}
					}
				});
	}
    /**
     * 从内存获取图片
     * @param subUrl 
     */
	private void getFromMemmery(final String subUrl) {
		final ImageInfo imageInfo = (ImageInfo) imageCache.getImageInfo(subUrl);
		if (imageInfo != null) {
			String imageType = imageInfo.getImageType();
			if ("GIF".equals(imageType)) {
				isRun = true;
				new GifDrawThread(imageInfo).start();
			} else {
				this.post(new Runnable() {
					
					@Override
					public void run() {
						setImageBitmap(shape.getBitmap(imageInfo.getBitmap(0)));
					}
				});
			}
		}
	}
   /**
    * 这是网络图片路径
    * @param httpPath
    */
	public void setHttpImage(final String httpPath) {
		setHttpImage(httpPath, null);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect rec = canvas.getClipBounds();
		if (borderwidth > 0) {
			paint.setColor(borderColor);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(borderwidth);
			canvas.drawRect(rec, paint);
		}
	}
    /**
     * 解析gif时调用该方法
     * @param parseStatus 为true表示解析成功
     * @param frameIndex  返回解析的第几帧，返回-1表示解析完毕。
     */
	public void parseOk(boolean parseStatus, int frameIndex) {
		if (parseStatus) {
			if (gifDecoder != null) {
				if (frameIndex == -1) {
					if (gifDecoder.getFrameCount() > 1) {
						Bitmap bitmaps[] = new Bitmap[gifDecoder
								.getFrameCount()];
						long delays[] = new long[gifDecoder.getFrameCount()];
						ImageInfo imageInfo = new ImageInfo();
						for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
							GifFrame frame = gifDecoder.next();
							bitmaps[i] = frame.image;
							delays[i] = frame.delay;
						}
						imageInfo.setBitmaps(bitmaps);
						imageInfo.setDelays(delays);
						imageInfo.setImageType("GIF");
						imageCache.addImageInfoToMemoryCache(subUrl,
								imageInfo);
						isRun = true;
						new GifDrawThread(imageInfo).start();
					} else {
						ImageInfo imageInfo = new ImageInfo();
						Bitmap[] bitmap = new Bitmap[1];
						bitmap[0] = gifDecoder.getImage();
						imageInfo.setBitmaps(bitmap);
						imageInfo.setImageType("DEFAULT");
						imageCache.addImageInfoToMemoryCache(subUrl,
								imageInfo);
							Message msg = new Message();
							msg.obj = shape.getBitmap(gifDecoder.getImage());
							loadhandler.sendMessage(msg);
					}
				}
			}
		} else {
			Log.e("gif", "parse error");
		}
	}
	

	/**
	 * 设置边框
	 * @param borderwidth
	 *            边框宽度
	 * @param borderColor
	 *            边框颜色
	 */
	public void setBorderwidth(int borderwidth, int borderColor) {
		this.borderwidth = borderwidth;
		this.borderColor = borderColor;
	}
   /**
    * 当该ImageView所在的窗口关闭时调用该方法。
    * gif绘制停止。
    */
	@Override
	protected void onDetachedFromWindow() {
		isRun = false;
		super.onDetachedFromWindow();
	}
    /**
     * @author limitvic
     * gif绘制线程
     */
	private class GifDrawThread extends Thread {
		private ImageInfo imageInfo;
		public GifDrawThread(ImageInfo imageInfo) {
			this.imageInfo = imageInfo;
		}
		public void run() {
			int i = 0;
			//如果要给gif设置形状，先把每一帧剪切的保存下来，用于绘制。
			//这样子就不需要在播放的时候一直剪切成指定形状，减少内存开销。
			cutBitmaps = ChangeToCutBitmap(imageInfo.getBitmaps());
			while (isRun) {
				if (i < cutBitmaps.length) {
					Message msg = new Message();
					msg.obj = cutBitmaps[i];
					loadhandler.sendMessage(msg);
					SystemClock.sleep(Math.max(imageInfo.getDelay(i), 100));
					i++;
					if (i == imageInfo.getFrameCount()) {
						i = 0;
					}
				}
			}
		}
	}
    /**
     * 将每一帧都剪切成指定形状。
     * @param bitmaps
     * @return
     */
	public Bitmap[] ChangeToCutBitmap(Bitmap[] bitmaps) {
		Bitmap[] cutBitmap = new Bitmap[bitmaps.length];
		for (int i = 0; i < bitmaps.length; i++) {
			cutBitmap[i] = shape.getBitmap(bitmaps[i]);
			if (cutBitmap[i] == bitmaps[i]) {
				ChangeBitmaps = false;
			}
		}
		return cutBitmap;
	}
   /**
    * 释放剪切后的图片。
    */
	public void releaseCutBitmap() {
		if (!ChangeBitmaps) {
			return;
		}
		if (cutBitmaps != null) {
			for (Bitmap bitmap : cutBitmaps) {
				bitmap.recycle();
				bitmap = null;
			}
		}
	}
   /**
    * 获取当前请求的网络地址
    * @return
    */
	public String getHttpPath() {
		return httpPathURL;
	}

}
