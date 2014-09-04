package com.atm.customimageview.test;
import com.atm.customimageview.show.CustomImageView;
import com.example.main.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;

public class CustomImageViewActivity extends Activity {
	private CustomImageView mcustomImageView;
	private CustomImageView mcustomImageView2;
    private CustomImageView mcustomImageView3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mcustomImageView = (CustomImageView) findViewById(R.id.customImageView);
		mcustomImageView.setHttpImage(
						"http://li.ivi.li/www.iqshw.com/d/file/gexingqqziyuan/touxiang/2012/09/11/eape2endxd4.gif",
						Environment.getExternalStorageDirectory().toString()	+ "/1.gif");
		mcustomImageView.setShape(CustomImageView.CIRCLE, 0);
		mcustomImageView.setSDCardCache(true);
		
		mcustomImageView2 = (CustomImageView) findViewById(R.id.customImageView2);
		mcustomImageView2.setShape(CustomImageView.CIRCLECORNERRECT,0);
		mcustomImageView2.setBorderwidth(10, Color.GREEN);
		mcustomImageView2.setLightness(0.5f);
		mcustomImageView2.setHttpImage("http://p.qq181.com/cms/1210/2012100413195471481.jpg");

		mcustomImageView3 = (CustomImageView) findViewById(R.id.customImageView3);
		mcustomImageView3.setHttpImage("http://c.hiphotos.bdimg.com/album/w%3D300%3Bcrop%3D0%2C25%2C300%2C300%3Bq%3D90/sign=cfb894ded3c8a786be2a4c0e5732aa4f/c995d143ad4bd1139957831658afa40f4bfb057f.jpg");
		mcustomImageView3.setShape(CustomImageView.CIRCLECORNERRECT, 20);
	}
}
