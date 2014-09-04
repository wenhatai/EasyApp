package com.atm.actionbar;

import com.atm.actionbar.widget.ActionBar;
import com.atm.actionbar.widget.OnActionBarClickListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.app.Activity;

/**
 * 抽象的ActionBarActivity基类，只要重写onCreateActionBar、
 * onActionBarLeftItemClick和onActionBarRightItemClick方法
 * 
 * @author cidgeedeng
 *
 */
public abstract class ActionBarActivity extends Activity implements OnActionBarClickListener{
	private LinearLayout mLinearLayout;
	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mLinearLayout = new LinearLayout(this);
		mActionBar = new ActionBar(this);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mActionBar.setOnActionBarListener(this);
		onCreateActionBar(mActionBar);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(layoutResID, null);
		mLinearLayout.removeAllViews();
		mLinearLayout.addView(mActionBar.getLayout());
		mLinearLayout.addView(view);
		super.setContentView(mLinearLayout);
	}

	@Override
	public void setContentView(View view) {
		mLinearLayout.removeAllViews();
		mLinearLayout.addView(mActionBar.getLayout());
		mLinearLayout.addView(view);
		super.setContentView(mLinearLayout);
	}
	
	@Override
	public void setContentView(View view, LayoutParams params) {
		mLinearLayout.removeAllViews();
		mLinearLayout.addView(mActionBar.getLayout());
		mLinearLayout.addView(view);
		super.setContentView(mLinearLayout, params);
	}
	
	public View getView() {
		return mLinearLayout;
	}
	
	/**
	 * 设置是否显示ActionBar
	 * @param enable
	 */
	public void setActionBarEnable(boolean enable) {
		mActionBar.getLayout().setVisibility(enable ? View.VISIBLE : View.GONE);
	}

	public void onCreateActionBar(ActionBar actionBar) {
	}

	public void onActionBarLeftItemClick() {
	}

	public void onActionBarRightItemClick(int id) {
	}

	@Override
	public void onLeftItemClick() {
		onActionBarLeftItemClick();
	}

	@Override
	public void onRightItemClick(int id) {
		onActionBarRightItemClick(id);
	}
	
	/**
	 * 设置actionBar上的progressBar可见性
	 * @param visibility
	 */
	public void setActionBarProgressBarVisibility(int visibility) {
		mActionBar.setProgressBarVisibility(visibility);
	}
}
