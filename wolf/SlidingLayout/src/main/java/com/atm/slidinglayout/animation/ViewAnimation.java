package com.atm.slidinglayout.animation;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * 自定义动画类
 * 
 * @author cidgeedeng
 *
 */
@SuppressLint("NewApi")
public class ViewAnimation {

	public static void runMoveToAnmation(final View view, int startX,
			final int endX, int startY, final int endY, final int duration) {
		final int x = startX;
		final int dx = (endX - startX) / (duration / 16);
		final int y = startY;
		final int dy = (endY - startY) / (duration / 16);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Point point = (Point) msg.obj;
				view.setX(point.x);
				view.setY(point.y);
			}
		};

		new Thread() {
			@Override
			public void run() {
				super.run();
				long startTime = System.currentTimeMillis();
				int count = 0;
				while (true) {
					if (count >= duration / 16) {
						break;
					}
					long nowTime = System.currentTimeMillis();
					if (nowTime - startTime >= 16) {
						startTime = nowTime;
						count++;
						Point point = new Point();
						point.x = x + dx * count;
						point.y = y + dy * count;
						Message msg = new Message();
						msg.obj = point;
						handler.sendMessage(msg);
					}
					try {
						sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Point point = new Point();
				point.x = endX;
				point.y = endY;
				Message msg = new Message();
				msg.obj = point;
				handler.sendMessage(msg);
			}
		}.start();
	}

	public static void runScrollAnmation(final View view, int startX,
			final int endX, int startY, final int endY, final int duration) {
		final int x = startX;
		final int dx = (endX - startX) / (duration / 16);
		final int y = startY;
		final int dy = (endY - startY) / (duration / 16);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Point point = (Point) msg.obj;
				view.scrollTo(point.x, point.y);
			}
		};

		new Thread() {
			@Override
			public void run() {
				super.run();
				long startTime = System.currentTimeMillis();
				int count = 0;
				while (true) {
					if (count >= duration / 16) {
						break;
					}
					long nowTime = System.currentTimeMillis();
					if (nowTime - startTime >= 16) {
						startTime = nowTime;
						count++;
						Point point = new Point();
						point.x = x + dx * count;
						point.y = y + dy * count;
						Message msg = new Message();
						msg.obj = point;
						handler.sendMessage(msg);
					}
					try {
						sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Point point = new Point();
				point.x = endX;
				point.y = endY;
				Message msg = new Message();
				msg.obj = point;
				handler.sendMessage(msg);
			}
		}.start();
	}

	public static void runScaleAnmation(final View view, float startScale,
			final float endScale, final int duration) {
		final float scale = startScale;
		final float dScale = (endScale - startScale) / (duration / 16);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Float scaleF = (Float) msg.obj;
				view.setScaleX(scaleF.floatValue());
				view.setScaleY(scaleF.floatValue());
			}
		};

		new Thread() {
			@Override
			public void run() {
				super.run();
				long startTime = System.currentTimeMillis();
				int count = 0;
				while (true) {
					if (count >= duration / 16) {
						break;
					}
					long nowTime = System.currentTimeMillis();
					if (nowTime - startTime >= 16) {
						startTime = nowTime;
						count++;
						Float scaleF = scale + dScale * count;
						Message msg = new Message();
						msg.obj = scaleF;
						handler.sendMessage(msg);
					}
					try {
						sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Float scaleF = endScale;
				Message msg = new Message();
				msg.obj = scaleF;
				handler.sendMessage(msg);
			}
		}.start();
	}

	public static void runAlphaAnmation(final View view, float startAlpha,
			final float endAlpha, final int duration) {
		final float alpha = startAlpha;
		final float dAlpha = (endAlpha - startAlpha) / (duration / 16);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Float alphaF = (Float) msg.obj;
				view.setAlpha(alphaF.floatValue());
			}
		};

		new Thread() {
			@Override
			public void run() {
				super.run();
				long startTime = System.currentTimeMillis();
				int count = 0;
				while (true) {
					if (count >= duration / 16) {
						break;
					}
					long nowTime = System.currentTimeMillis();
					if (nowTime - startTime >= 16) {
						startTime = nowTime;
						count++;
						Float alphaF = alpha + dAlpha * count;
						Message msg = new Message();
						msg.obj = alphaF;
						handler.sendMessage(msg);
					}
					try {
						sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Float alphaF = endAlpha;
				Message msg = new Message();
				msg.obj = alphaF;
				handler.sendMessage(msg);
			}
		}.start();
	}
}
