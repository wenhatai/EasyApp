package com.atm.locationselector.fixedposition;

import com.atm.locationselector.bean.LocationBean;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
/**
 * 
 * created by limingzhang on 2014/8/10
 *
 */
public class LocationGetter {
	private Handler handler;
	

	@SuppressLint("HandlerLeak")
	public void setLocationAddress(Context context, final CallBack callBack) {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				callBack.getLocation((LocationBean)msg.obj);
			}
		};
		new GetLocationThread(handler, context).start();
	}


	class GetLocationThread extends Thread implements TencentLocationListener {
		private String name="" ;
		private LocationBean locationBean;
		private TencentLocationRequest request = TencentLocationRequest
				.create();
		private TencentLocationManager mLocationManager;
		private Handler handler;

		public GetLocationThread(Handler handler, Context context) {
			this.handler = handler;
			request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
			mLocationManager = TencentLocationManager.getInstance(context);
			mLocationManager.requestLocationUpdates(request, this);
		}

		@Override
		public void run() {
			long post = System.currentTimeMillis();
			long now = 0 ;
			while(locationBean==null){
				now = System.currentTimeMillis();
				
					if(now-post>60000){
						Message msg = new Message();
						msg.obj = locationBean;
						handler.sendMessage(msg);
						mLocationManager.removeUpdates(this);
						break;
			
				}
			}
			Message msg = new Message();
			msg.obj = locationBean;
			handler.sendMessage(msg);
			mLocationManager.removeUpdates(this);
		}

		@Override
		public void onLocationChanged(TencentLocation location, int arg1,
				String arg2) {
			name = location.getName();
			location.getAddress();
			location.getLatitude();
			location.getLongitude();
			if(name!=""){
			locationBean =  new LocationBean();
			locationBean.setName(name);
			locationBean.setAddress(location.getAddress());
			locationBean.setLatitude(location.getLatitude());
			locationBean.setLongitude(location.getLongitude());
			}
		}

		@Override
		public void onStatusUpdate(String arg0, int arg1, String arg2) {
		}
	}

	public interface CallBack {
		public void getLocation(LocationBean locationBean);
	}
}
