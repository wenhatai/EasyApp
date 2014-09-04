package com.atm.slidinglayout;

/**
 * 滑动监听类
 * 
 * @author cidgeedeng
 *
 */
public interface OnSlidingLinstener {
	/**
	 * 滑动过程中
	 * 
	 * @param slideDirection
	 *            方向，1为向左滑动，2为向右滑动
	 * @param startX
	 * @param moveX
	 * @param screenWidth
	 * @param density
	 */
	public void onSlidingScroll(int slideDirection, float startX, float moveX,
                                int screenWidth, float density);

	/**
	 * 滑动结束
	 * 
	 * @param slideDirection
	 *            方向，1为向左滑动，2为向右滑动
	 * @param startX
	 * @param moveX
	 * @param screenWidth
	 * @param density
	 */
	public void onSlidingStop(int slideDirection, float startX, float moveX,
                              int screenWidth, float density);
}
