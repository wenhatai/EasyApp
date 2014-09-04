package com.atm.actionbar.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.atm.actionbar.ActionBarActivity;
import com.atm.actionbar.widget.ActionBar;
import com.example.main.R;

/**
 * 
 * @author cidgeedeng
 *
 */
public class ActionBarTestActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actionbar);
		setActionBarEnable(true);
		// 设置进度条是否可见
		setActionBarProgressBarVisibility(View.VISIBLE);
	}

	@Override
	public void onCreateActionBar(ActionBar actionBar) {
		actionBar.setLeftItem("返回", R.drawable.back_icon);
		actionBar.addRightItem("管理", 0);
		actionBar.setCenterText("ActionBar");

		super.onCreateActionBar(actionBar);
	}

	@Override
	public void onActionBarLeftItemClick() {
		finish();
		super.onActionBarLeftItemClick();
	}

	@Override
	public void onActionBarRightItemClick(int id) {
		switch (id) {
		case 0:
			Toast.makeText(this, "管理", Toast.LENGTH_SHORT).show();
		}
		super.onActionBarRightItemClick(id);
	}
}
