package com.atm.locationselector.test;

import com.atm.locationselector.bean.LocationBean;
import com.atm.locationselector.fixedposition.LocationActivity;
import com.atm.locationselector.fixedposition.LocationGetter;
import com.atm.locationselector.fixedposition.LocationGetter.CallBack;
import com.example.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocationSelectorActivity extends Activity {
	private Button btn;
	private LocationGetter locationgetter = new LocationGetter();
	private LocationBean locationBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_selector);
		btn = (Button) findViewById(R.id.locationBtn);
		if (locationBean == null) {
			locationgetter.setLocationAddress(this, new CallBack() {
				@Override
				public void getLocation(LocationBean locationBean) {
					// TODO Auto-generated method stub
					LocationSelectorActivity.this.locationBean = locationBean;
					if (locationBean != null) {
						btn.setText(locationBean.getName());
					} else {
						btn.setText("定位失败");
					}
				}
			});
		} else {
			btn.setText(locationBean.getName());
		}
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (locationBean == null) {
					return;
				}
				Intent intent = new Intent(LocationSelectorActivity.this,
						LocationActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("location", locationBean);
				intent.putExtra("locationbuddle", bundle);
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == LocationActivity.FROMLOCATION_CODE) {
			locationBean = (LocationBean) data.getBundleExtra("locationbuddle")
					.getSerializable("location");
			btn.setText(locationBean.getName());
		}
	}

}
