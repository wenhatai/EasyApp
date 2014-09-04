package com.atm.slidinglayout.test;
import com.atm.slidinglayout.SlidingLayout;
import com.example.main.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;

/**
 * 
 * @author cidgeedeng
 *
 */
public class SlidingLayoutActivity extends Activity {
	private SlidingLayout mSlidingLayout;
	private LinearLayout mMenuLayout;
	private LinearLayout mContentLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slidinglayout);
		mSlidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
		mMenuLayout = (LinearLayout) findViewById(R.id.menuLayout);
		mContentLayout = (LinearLayout) findViewById(R.id.contentLayout);
		mSlidingLayout.setCanSlide(false, true);
		mSlidingLayout.setView(mContentLayout, mMenuLayout);
	}
}
