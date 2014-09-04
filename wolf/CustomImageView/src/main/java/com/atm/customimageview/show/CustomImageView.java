package com.atm.customimageview.show;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.atm.customimageview.shape.Circle;
import com.atm.customimageview.shape.CircleRect;
import com.atm.customimageview.shape.Shape;
import com.atm.customimageview.tool.BitmapCompress;
import com.atm.customimageview.tool.DownLoadUtil;
import com.atm.customimageview.tool.ImageCache;
import com.atm.customimageview.tool.SdCacheUtil;
import com.example.customimageview.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 
 * @author limingzhang
 *
 */
@SuppressLint({ "DrawAllocation", "HandlerLeak" })
public class CustomImageView extends ImageView implements GifAction {

	private GifDecoder gifDecoder = null;
	private Bitmap currentgifImage = null;
	private Bitmap currentstaticImage = null;
	private boolean isGifImage = false;
	private boolean isRun = true;
	protected Context mContext;
	private boolean pause = false;
	private byte[] getbt;
	private Shape shape = new CircleRect(0);
	private int borderwidth = 0;
	private int borderColor = Color.WHITE;
	public final static int CIRCLE = 0;
	public final static int CIRCLECORNERRECT = 1;
	private int compresswidth = 0;
	private int compressheight = 0;
	private DrawThread drawThread = null;
	private GifImageType animationType = GifImageType.WAIT_FINISH;
	private Matrix matrix = new Matrix();
	private ImageCache imageCache = ImageCache.getInstance();
	private SdCacheUtil sdCacheUtil = SdCacheUtil.getInstance(getContext());
	private boolean SDCardCache = false;
	private boolean isloading = false; // 璇ユ帶浠舵槸鍚︽鍦ㄥ姞杞藉浘鐗�
	private boolean iscompressed = false;// 鏄惁闇�瑕佸帇缂�
	private String httpPath = "";
	private int masRequestnum = 3;

	public CustomImageView(Context context) {
		super(context);

	}

	public CustomImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public enum GifImageType {

		WAIT_FINISH(0), SYNC_DECODER(1), COVER(2);
		GifImageType(int i) {
			nativeInt = i;
		}

		final int nativeInt;
	}

	public void setShape(int shapeindex, int outerRadiusRat) {
		if (shapeindex == CIRCLE) {
			shape = new Circle();
		} else if (shapeindex == CIRCLECORNERRECT) {
			shape = new CircleRect(outerRadiusRat);
		}
	}

	private boolean isSDCardCache() {
		return SDCardCache;
	}

	public void setSDCardCache(boolean sDCardCache) {
		SDCardCache = sDCardCache;
	}

	public void setLightness(float lightness) {
		shape.setLightness(lightness);
	}

	private void setGifDecoderImage(byte[] gif) {
		if (gifDecoder != null) {
			gifDecoder.free();
			gifDecoder = null;
		}
		gifDecoder = new GifDecoder(gif, this);
		isRun = true;
		gifDecoder.start();

	}

	private void setGifDecoderImage(InputStream is) {
		if (gifDecoder != null) {
			gifDecoder.free();
			gifDecoder = null;
		}
		gifDecoder = new GifDecoder(is, this);
		pause = false;
		gifDecoder.start();
	}

	public void setGifImage(byte[] gif) {
		setGifDecoderImage(gif);
	}

	private boolean isGif(InputStream is) {
		String id = "";
		try {
			for (int i = 0; i < 6; i++) {

				id += (char) is.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (id.startsWith("GIF")) {
			return true;
		} else {
			return false;
		}
	}

	public void setGifImage(int resId) {
		Resources r = this.getResources();
		InputStream is = r.openRawResource(resId);
		setGifDecoderImage(is);
	}

	public Handler loadhandler = new Handler() {
		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x01:

				isloading = false;
				getbt = (byte[]) msg.obj;

				InputStream inputStream = new ByteArrayInputStream(getbt);
				if (isGif(inputStream)) {
					isGifImage = true;
					isRun = true;
					setGifDecoderImage(getbt);

				} else {
					isGifImage = false;
					currentstaticImage = BitmapFactory.decodeByteArray(getbt,
							0, getbt.length);
					if(currentstaticImage==null){
						return;
					}
					if (currentstaticImage.getWidth() <= 0 || getWidth() <= 0) {
						if (masRequestnum-- > 0) {
							setHttpImage(httpPath);
						}// 澶辫触閲嶆柊璇锋眰
						return;
					}
					if (!iscompressed) {
						float w = (float) getWidth()
								/ (float) currentstaticImage.getWidth();
						float h = (float) getHeight()
								/ (float) currentstaticImage.getHeight();
						if (w > h) {
							matrix.setScale(w, w);
						} else {
							matrix.setScale(h, h);
						}
						if (currentstaticImage.getWidth()<=0) {
							currentstaticImage = BitmapFactory.decodeResource(
									getResources(),
									R.drawable.photoselectorlib_pictures_no);
						} else {
							currentstaticImage = Bitmap.createBitmap(
									currentstaticImage, 0, 0,
									currentstaticImage.getWidth(),
									currentstaticImage.getHeight(), matrix,
									false);
						}
					} else {
						currentstaticImage = BitmapCompress.getCompressBitmap(
								currentstaticImage, compresswidth,
								compressheight);
					}
					currentstaticImage = shape.getBitmap(currentstaticImage);

					invalidate();
				}
			}
		};
	};

	public void setHttpImage(final String httpPath, final String sdPath) {
		this.httpPath = httpPath;
		if (!isloading) {
			isRun = false;
			isloading = true;
			isGifImage = false;
			gifDecoder = null;
			final String subUrl = httpPath.replaceAll("[^\\w]", "");

			if (imageCache.getCacheByte(subUrl) == null) {
				if (!sdCacheUtil.isFileExists(subUrl)) {
					try {
						DownLoadUtil.getInstance().downloadFile(httpPath,
								sdPath, new DownLoadUtil.CallBack() {

									@Override
									public void downLoadByte(byte[] bt) {
										// TODO Auto-generated method stub
										if (bt != null) {
											Message message = new Message();
											message.what = 0x01;
											message.obj = bt;
											loadhandler.sendMessage(message);

											imageCache.addBitmapToMemoryCache(
													subUrl, bt);

											if (isSDCardCache()) {
												try {
													sdCacheUtil.savaByte(
															subUrl, bt);
												} catch (IOException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											}
										}
									}
								});

					} catch (Exception e) {
					}
				} else {
					sdCacheUtil.getByteFromSDCard(subUrl,
							new SdCacheUtil.CallBack() {

								@Override
								public void get(byte[] bt) {
									// TODO Auto-generated method stub
									Message message = new Message();
									message.what = 0x01;
									message.obj = bt;
									loadhandler.sendMessage(message);
									imageCache.addBitmapToMemoryCache(subUrl,
											bt);
								}

								@Override
								public void getSize(String size) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void clear(String status) {
									// TODO Auto-generated method stub
									
								}
							});

				}
			} else {
				Message message = new Message();
				message.what = 0x01;
				message.obj = imageCache.getCacheByte(subUrl);
				loadhandler.sendMessage(message);
			}
		}
	}

	public void setHttpImage(final String httpPath) {
		setHttpImage(httpPath, null);
	}

	protected void onDraw(Canvas canvas) {

		if (!isloading) {

			if (isGifImage) {

				if (gifDecoder == null)
					return;
				if (currentgifImage == null) {
					currentgifImage = gifDecoder.getImage();
				}
				if (currentgifImage == null) {
					return;
				}
				int saveCount = canvas.getSaveCount();
				canvas.save();
				canvas.translate(getPaddingLeft(), getPaddingTop());
				float w = (float) getWidth()
						/ (float) currentgifImage.getWidth();
				float h = (float) getHeight()
						/ (float) currentgifImage.getHeight();
				matrix.setScale(w, h);
				currentgifImage = shape.getBitmap(currentgifImage);
				canvas.drawBitmap(currentgifImage, matrix, null);
				canvas.restoreToCount(saveCount);
			} else {

				if (currentstaticImage != null) {
					canvas.drawBitmap(currentstaticImage, 0, 0, null);
				} else {
					super.onDraw(canvas);
				}
			}
			Rect rec = canvas.getClipBounds();
			Paint paint = new Paint();
			if (borderwidth > 0) {
				paint.setColor(borderColor);
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(borderwidth);
				canvas.drawRect(rec, paint);
			}
		}
	}

	public void showCover() {
		if (gifDecoder == null)
			return;
		pause = true;
		currentgifImage = gifDecoder.getImage();
		invalidate();
	}

	public void showAnimation() {
		if (pause) {
			pause = false;
		}
	}

	public void setGifImageType(GifImageType type) {
		if (gifDecoder == null)
			animationType = type;
	}

	public void parseOk(boolean parseStatus, int frameIndex) {
		if (parseStatus) {
			if (gifDecoder != null) {
				switch (animationType) {
				case WAIT_FINISH:
					if (frameIndex == -1) {
						if (gifDecoder.getFrameCount() > 1) {
							DrawThread dt = new DrawThread();
							dt.start();
						} else {
							reDraw();
						}
					}
					break;
				case COVER:
					if (frameIndex == 1) {
						currentgifImage = gifDecoder.getImage();
						reDraw();
					} else if (frameIndex == -1) {
						if (gifDecoder.getFrameCount() > 1) {
							if (drawThread == null) {
								drawThread = new DrawThread();
								drawThread.start();
							}
						} else {
							reDraw();
						}
					}
					break;
				case SYNC_DECODER:
					if (frameIndex == 1) {
						currentgifImage = gifDecoder.getImage();
						reDraw();
					} else if (frameIndex == -1) {
						reDraw();
					} else {
						if (drawThread == null) {
							drawThread = new DrawThread();
							drawThread.start();
						}
					}
					break;
				}

			} else {
				Log.e("gif", "parse error");
			}

		}
	}

	private void reDraw() {
		if (redrawHandler != null) {
			Message msg = redrawHandler.obtainMessage();
			redrawHandler.sendMessage(msg);
		}
	}

	private Handler redrawHandler = new Handler() {
		public void handleMessage(Message msg) {
			isGifImage = true;
			invalidate();
		}
	};

	/**
	 * 
	 * @param borderwidth
	 *            杈规瀹藉害
	 * @param borderColor
	 *            杈规棰滆壊
	 */
	public void setBorderwidth(int borderwidth, int borderColor) {
		this.borderwidth = borderwidth;
		this.borderColor = borderColor;
	}

	public void setCompressImage(int width, int height) {
		if (width > 0 && height > 0) {
			this.compresswidth = width;
			this.compressheight = height;
			iscompressed = true;
		}

	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		isRun = false;
	}

	private class DrawThread extends Thread {
		public void run() {
			while (isRun) {
				if (gifDecoder == null) {
					break;
				}
				if (pause == false) {
					GifFrame frame = gifDecoder.next();
					if (frame == null) {
						break;
					}
					currentgifImage = frame.image;
					long sp = frame.delay;
					if (redrawHandler != null) {
						redrawHandler.sendEmptyMessage(0);
						SystemClock.sleep(sp);
					} else {
						break;
					}

				} else {
					SystemClock.sleep(10);
				}
			}
/*			if (gifDecoder == null) {
				return;
			}
			if (pause == false) {
				GifFrame frame = gifDecoder.next();
				currentgifImage = frame.image;
				while(currentgifImage==null){
					frame = gifDecoder.next();
					currentgifImage = frame.image;
				}
				if(currentgifImage!=null){
					if (redrawHandler != null) {
						redrawHandler.sendEmptyMessage(0);
					} 
				}
			}*/
		}
	}

	public String getHttpPath() {
		return httpPath;
	}
	
	

}
