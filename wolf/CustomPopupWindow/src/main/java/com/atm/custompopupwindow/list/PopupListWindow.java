package com.atm.custompopupwindow.list;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.atm.custompopupwindow.R;

public class PopupListWindow extends PopupWindow {


	private TextView cancel;
	private ListView popupListView;
	private View mMenuView;

	public PopupListWindow(final Activity context,String[] item,OnItemClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popupwindow, null);
		popupListView = (ListView) mMenuView.findViewById(R.id.poplist);
		
		List<String> popupList = Arrays.asList(item); 
		PopupListAdapter popupAdapter = new PopupListAdapter(context,popupList);
		popupListView.setAdapter(popupAdapter);
		popupListView.setOnItemClickListener(itemsOnClick);
		cancel = (TextView) mMenuView.findViewById(R.id.cancel);
		//取消按钮
		cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//销毁弹出框
				dismiss();
			}
		});
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点
		this.setFocusable(true);
		//设置窗口动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x50000000);
		//设置SelectPicPopupWindow弹出窗体的背背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});

	}

}
