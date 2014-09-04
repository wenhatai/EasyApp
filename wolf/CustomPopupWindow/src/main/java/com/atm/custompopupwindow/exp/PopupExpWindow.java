package com.atm.custompopupwindow.exp;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atm.custompopupwindow.R;

public class PopupExpWindow {
	private View view;
	private PopupWindow pw;
	private static PopupExpWindow popupExpWindow;
	private Context context;
	private LayoutInflater inflate;
	private View popView;
    private String addnum;
	public PopupExpWindow(View view, int addnum, Context context) {
		this.view = view;
		inflate = LayoutInflater.from(context);
	    this.addnum = "+"+addnum;
	}

	public void startPop() {
		if (pw == null) {
			popView = inflate.inflate(R.layout.exppop, null);
			pw = new PopupWindow(popView, view.getWidth(), view.getHeight() * 3);
		}
		RelativeLayout rl = (RelativeLayout)popView.findViewById(R.id.pop);
		TextView tc  = (TextView)popView.findViewById(R.id.value2);
		tc.setText(addnum);
		pw.setAnimationStyle(0);
		pw.showAsDropDown(view, 0,
				-(view.getHeight()*3));
		/*TranslateAnimation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);*/
		TranslateAnimation animation = new TranslateAnimation(view.getWidth()/8,view.getWidth()/8,0,-view.getHeight());
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
				if (pw != null) {
					new Handler().post(new Runnable() {
						@Override
						public void run() {
							pw.dismiss();
						}
					});
				}	
				}
		});
		rl.startAnimation(animationSet);
	}
}
