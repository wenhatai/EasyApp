package com.atm.slidinglayout;

import com.atm.slidinglayout.animation.ViewAnimation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 滑动布局，检测滑动事件
 * 使用时，将SlidingLayout放于底层，再于上层分别添加背景层，菜单层以及内容层
 * 通过setView传递菜单层以及内容层以使滑动时显示侧滑效果
 * 
 * @author cidgeedeng
 *
 */
@SuppressLint("NewApi")
public class SlidingLayout extends RelativeLayout {
	private int mScreenWidth;
	private float mDensity;
	private Context mContext;
	private int mTouchSlop;
	private float mLastMotionX;
	private float mLastMotionY;
	private boolean mIsBeingDragged = true;
	// 左滑
	private boolean mIsCanSlideLeft = false;
	// 右滑
	private boolean mIsCanSlideRight = false;
	private OnSlidingLinstener mOnSlidingLinstener;
	private View mContentView;
	private View mMenuView;

	public SlidingLayout(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public SlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	/**
	 * 设置内容view，菜单view以及背景view的引用，非空时采用默认的滑动效果
	 * @param contentView
	 * @param menuView
	 */
	public void setView(View contentView, View menuView) {
		mContentView = contentView;
		mMenuView = menuView;
	}

	/**
	 * 是否能滑动
	 * 
	 * @param isCanSlideLeft
	 *            向左滑动
	 * @param isCanSlideRight
	 *            向右滑动
	 */
	public void setCanSlide(boolean isCanSlideLeft, boolean isCanSlideRight) {
		mIsCanSlideLeft = isCanSlideLeft;
		mIsCanSlideRight = isCanSlideRight;
	}

	/**
	 * 设置滑动监听
	 * 
	 * @param l
	 */
	public void setOnSlidingLinstener(OnSlidingLinstener l) {
		mOnSlidingLinstener = l;
	}

	@SuppressWarnings("deprecation")
	public void init() {
		new Scroller(getContext());
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		WindowManager windowManager = ((Activity) mContext).getWindow()
				.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		mScreenWidth = display.getWidth();
		display.getHeight();
		mDensity = dm.density;
	}

	// 拦截touch事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mLastMotionY = y;
			mIsBeingDragged = true;
			break;
		case MotionEvent.ACTION_MOVE:
			final float dx = x - mLastMotionX;
			final float xDiff = Math.abs(dx);
			final float yDiff = Math.abs(y - mLastMotionY);
			if (xDiff > mTouchSlop && xDiff > yDiff) {
				if (mIsCanSlideRight) {
					float oldScrollX = getScrollX();
					if (oldScrollX < 0) {
						mIsBeingDragged = true;
						mLastMotionX = x;
					} else {
						if (dx > 0) {
							mIsBeingDragged = true;
							mLastMotionX = x;
						}
					}
				} else if (mIsCanSlideLeft) {
					float oldScrollX = getScrollX();
					if (oldScrollX > 0) {
						mIsBeingDragged = true;
						mLastMotionX = x;
					} else {
						if (dx < 0) {
							mIsBeingDragged = true;
							mLastMotionX = x;
						}
					}
				}
			}
			break;
		}
		return mIsBeingDragged;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		final float x = ev.getX();
		ev.getY();

		if (mIsCanSlideLeft || mIsCanSlideRight) {
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				onSlidingScroll(mIsCanSlideLeft ? 1 : 2, mLastMotionX, x,
						mScreenWidth, mDensity);
				if (mOnSlidingLinstener != null) {
					mOnSlidingLinstener.onSlidingScroll(
							mIsCanSlideLeft ? 1 : 2, mLastMotionX, x,
							mScreenWidth, mDensity);
				}
				break;
			case MotionEvent.ACTION_UP:
				onSlidingStop(mIsCanSlideLeft ? 1 : 2, mLastMotionX, x,
						mScreenWidth, mDensity);
				if (mOnSlidingLinstener != null) {
					mOnSlidingLinstener.onSlidingStop(mIsCanSlideLeft ? 1 : 2,
							mLastMotionX, x, mScreenWidth, mDensity);
				}
				break;
			}
		}
		return true;
	}
	
    /**
     * 显示侧滑菜单
     */
	private void onSlidingScroll(int slideDirection, float startX, float moveX, int screenWidth, float density) {
		System.out.println(startX - moveX + "   " + slideDirection);
		if (slideDirection == 1) {
			float dx = screenWidth - 120 * density - (startX - moveX);
			if (dx < 0) {
				dx = 0;
			} else if (dx > screenWidth - 120 * density) {
				dx = screenWidth - 120 * density;
			}
			float scale = 1 - dx * 0.3f / screenWidth;
			if (mContentView != null) {
				mContentView.setScaleX(scale);
				mContentView.setScaleY(scale);
				mContentView.setX(dx);
			}
			if (mMenuView != null) {
				float menuScale = dx / (screenWidth - 120 * density) * 0.4f + 0.6f;
				mMenuView.setScaleX(menuScale);
				mMenuView.setScaleY(menuScale);
				mMenuView.setX(dx - (screenWidth - 120 * density));
			}
		} else {
			float dx = moveX - startX;
			if (dx < 0) {
				dx = 0;
			} else if (dx > screenWidth - 120 * density) {
				dx = screenWidth - 120 * density;
			}
			float scale = 1 - dx * 0.3f / screenWidth;
			if (mContentView != null) {
				mContentView.setScaleX(scale);
				mContentView.setScaleY(scale);
				mContentView.setX(dx);
			}
			if (mMenuView != null) {
				float menuScale = dx / (screenWidth - 120 * density) * 0.4f + 0.6f;
				mMenuView.setScaleX(menuScale);
				mMenuView.setScaleY(menuScale);
				mMenuView.setX(dx);
				mMenuView.setX(dx -(screenWidth - 120 * density));
			}
		}
	}

	/**
	 * 滑动停止时的滚动
	 */
	private void onSlidingStop(int slideDirection, float startX, float moveX, int screenWidth, float density) {
		if (slideDirection == 1) {
			if (startX - moveX < screenWidth / 3) {
				showMenu();
			} else {
				hideMenu();
			}
		} else {
			float dx = moveX - startX;
			if (dx > screenWidth / 3) {
				showMenu();
			} else {
				hideMenu();
			}
		}
	}
	
	/**
	 * 显示侧滑菜单
	 */
	public void showMenu() {
		float dx = mScreenWidth - 120 * mDensity;
		float scale = 1 - dx * 0.3f / mScreenWidth;
		ViewAnimation.runScaleAnmation(mContentView, mContentView.getScaleX(), scale, 200);
		ViewAnimation.runMoveToAnmation(mContentView, (int) mContentView.getX(), (int) dx, 0, 0, 200);
		ViewAnimation.runMoveToAnmation(mMenuView, (int) mMenuView.getX(), (int) 0, 0, 0, 200);
		ViewAnimation.runScaleAnmation(mMenuView, mMenuView.getScaleX(), 1, 200);
		setCanSlide(true, false);
	}
	
	/**
	 * 隐藏菜单
	 */
	public void hideMenu() {
		ViewAnimation.runScaleAnmation(mContentView, mContentView.getScaleX(), 1, 200);
		ViewAnimation.runMoveToAnmation(mContentView, (int) mContentView.getX(), 0, 0, 0, 200);
		ViewAnimation.runMoveToAnmation(mMenuView, (int) mMenuView.getX(), (int) (int) -(mScreenWidth - 120 * mDensity), 0, 0, 200);
		ViewAnimation.runScaleAnmation(mMenuView, mMenuView.getScaleX(), 0.6f, 200);
		setCanSlide(false, true);
	}
}
