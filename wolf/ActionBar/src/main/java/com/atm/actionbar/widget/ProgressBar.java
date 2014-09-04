package com.atm.actionbar.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ProgressBar extends RelativeLayout{
	private ImageView mView;
	
	public ProgressBar(Context context) {
		super(context);
		init();
	}

	public ProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
		LayoutParams params = new LayoutParams((int) (3 * dm.density), LayoutParams.MATCH_PARENT);
		mView = new ImageView(getContext());
		mView.setBackgroundColor(Color.BLUE);
		mView.setLayoutParams(params);
		setBackgroundColor(Color.rgb(30, 200, 192));
		addView(mView);
		TranslateAnimation moveRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				1, Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0);
		moveRight.setDuration(1000);
		moveRight.setRepeatMode(Animation.REVERSE);
		moveRight.setRepeatCount(Animation.INFINITE);
		mView.startAnimation(moveRight);
	}
}
