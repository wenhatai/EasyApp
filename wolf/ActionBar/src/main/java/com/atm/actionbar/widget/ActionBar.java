package com.atm.actionbar.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atm.actionbar.R;

/**
 * 
 * @author cidgeedeng
 *
 */
public class ActionBar {
	private LinearLayout mLeftView;
	private LinearLayout mRightLayout;
	private TextView mCenterText;
	private RelativeLayout mActionBarLayout;
	private Context mContext;
	private ProgressBar mProgressBar;
	private OnActionBarClickListener mActionBarListener;

	public ActionBar(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		mActionBarLayout = (RelativeLayout) inflater.inflate(R.layout.layout_actionbar, null);
		mLeftView = (LinearLayout) mActionBarLayout.findViewById(R.id.actionbarLeftView);
		mRightLayout = (LinearLayout) mActionBarLayout.findViewById(R.id.actionbarRightLayout);
		mCenterText = (TextView) mActionBarLayout.findViewById(R.id.actionbarCenterText);
		mProgressBar = (ProgressBar) mActionBarLayout.findViewById(R.id.actionProgressBar);
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	/**
	 * 设置左边按钮Item
	 * 
	 * @param text
	 *            文字
	 * @param iconResId
	 *            图标资源id
	 */
	public void setLeftItem(String text, int iconResId) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		RelativeLayout item = (RelativeLayout) inflater.inflate(
				R.layout.item_actionbar, null);
		TextView textView = (TextView) item.findViewById(R.id.actionBarItemText);
		ImageView icon = (ImageView) item.findViewById(R.id.actionBarItemIcon);
		textView.setText(text);
		icon.setImageResource(iconResId);
		mLeftView.removeAllViews();
		mLeftView.addView(item);
		item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mActionBarListener != null) {
					mActionBarListener.onLeftItemClick();
				}
			}
		});
	}

	/**
	 * 设置左边按钮Item
	 * 
	 * @param text
	 *            文字
	 */
	public void setLeftItem(String text) {
		setLeftItem(text, 0);
	}
	
	
	/**
	 * 设添加右边按钮Item
	 * 
	 * @param text
	 *            文字
	 * @param iconResId
	 *            图标资源id
	 * @param id
	 *            item的id
	 */
	public void addRightItem(String text, int iconResId, int id) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		final RelativeLayout item = (RelativeLayout) inflater.inflate(
				R.layout.item_actionbar, null);
		TextView textView = (TextView) item.findViewById(R.id.actionBarItemText);
		ImageView icon = (ImageView) item.findViewById(R.id.actionBarItemIcon);
		textView.setText(text);
		icon.setImageResource(iconResId);
		item.setId(id);
		
		mRightLayout.addView(item);
		item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mActionBarListener != null) {
					mActionBarListener.onRightItemClick(item.getId());
				}
			}
		});
	}

	/**
	 * 设添加右边按钮Item
	 * 
	 * @param text
	 *            文字
	 * @param id
	 *            item的id
	 */
	public void addRightItem(String text, int id) {
		addRightItem(text, 0, id);
	}

	public void removeRightItems() {
		mRightLayout.removeAllViews();
	}
	
	/**
	 * 设置中间标题
	 * 
	 * @param text
	 */
	public void setCenterText(String text) {
		mCenterText.setText(text);
	}

	public RelativeLayout getLayout() {
		return mActionBarLayout;
	}

	public void setOnActionBarListener(OnActionBarClickListener l) {
		mActionBarListener = l;
	}
	
	public void setProgressBarVisibility(int visibility) {
		mProgressBar.setVisibility(visibility);
	}
}
