package com.atm.custompopupwindow.test;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.atm.custompopupwindow.exp.PopupExpWindow;
import com.atm.custompopupwindow.like.PopupLikeWindow;
import com.atm.custompopupwindow.list.PopupListWindow;
import com.example.main.R;

public class CustomPopupWindowActivity extends Activity {
    PopupListWindow menuWindow;
    private Button btn;
    private PopupLikeWindow mLikeButtom;
    private PopupExpWindow epd;
    private LinearLayout linearLayout;
    private  Button btn2;
    private String[] str = new String[]{"今天天气不错","早上好","我在这里","哇哈哈"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custompopupwindow);
		btn = (Button) findViewById(R.id.btn);
        btn2 = (Button)findViewById(R.id.btn2);
        mLikeButtom = (PopupLikeWindow)findViewById(R.id.like_btn);
        linearLayout = (LinearLayout)findViewById(R.id.mainview);
        epd= new PopupExpWindow(linearLayout,2,this);
        mLikeButtom.init(this);
        mLikeButtom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mLikeButtom.like();
            }

        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                epd.startPop();
            }
        });
	}
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			menuWindow.dismiss();
			Toast.makeText(CustomPopupWindowActivity.this,str[position],Toast.LENGTH_SHORT).show();
		}
	};
	    
	 public void btnClick (View v){
			//实例化SelectPicPopupWindow
			menuWindow = new PopupListWindow(CustomPopupWindowActivity.this, str,itemClickListener);
			//显示窗口
			menuWindow.showAtLocation(CustomPopupWindowActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	 }

}
