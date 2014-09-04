package com.atm.custompopupwindow.like;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.atm.custompopupwindow.R;

/**
 * 
 * @author limingzhang 给按钮设置一个监听器，在回调在回来的onClicked方法里面回调onLikeClike。
 * 
 */
public class PopupLikeWindow extends TextView{
	private Context context;
	private ImageView imageView;
	private PopupWindow pw;
	private RelativeLayout rl;
	public PopupLikeWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public PopupLikeWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PopupLikeWindow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 点赞
	 */
	public void like(){
		if (pw == null) {
			imageView = new ImageView(context);
			rl = new RelativeLayout(context);
			rl.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			imageView.setImageResource(R.drawable.like_bg);
			Bitmap bitmap = BitmapFactory.decodeResource(
					getResources(), R.drawable.like_bg);
			lp.topMargin = getHeight() * 2 - bitmap.getHeight();
			imageView.setLayoutParams(lp);
			rl.addView(imageView);
			pw = new PopupWindow(rl, getWidth(), getHeight() * 2);
		}
		pw.setAnimationStyle(0);
		pw.showAsDropDown(this, 0, -(getHeight() * 2 + getHeight() / 2));
		TranslateAnimation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, -1);
		AlphaAnimation animation2 = new AlphaAnimation(0, 1);
		final AlphaAnimation animation3 = new AlphaAnimation(1, 0);
		animation3.setDuration(1000);
		AccelerateDecelerateInterpolator adi = new AccelerateDecelerateInterpolator();
		animation.setDuration(1000);// 设置动画持续时
		animation2.setDuration(1000);
		animation3.setStartOffset(2000); // 延迟播放
		final AnimationSet animationSet = new AnimationSet(true);
		animationSet.setInterpolator(adi);
		animationSet.addAnimation(animation);
		animationSet.addAnimation(animation2);
		animationSet.addAnimation(animation3);
		animationSet.setFillAfter(true);// 动画执行结束后，控件停留在结束的状态
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				pw.dismiss();
			}
		});
		imageView.startAnimation(animationSet);
	}
	/**
	 * 
	 * @param context 当前Activity的Context
	 */
	public void init(Context context) {  
		this.context = context;
	}
	
}
