package com.atm.locationselector.fixedposition;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.atm.locationselector.R;
import com.atm.locationselector.bean.LocationBean;
import com.atm.locationselector.bean.PoiBean;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.map.GeoPoint;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.search.PoiItem;
import com.tencent.tencentmap.mapsdk.search.PoiResults;
import com.tencent.tencentmap.mapsdk.search.PoiSearch;

/**
 * 
 * created by limingzhang on 2014/8/10
 *
 */
public class LocationActivity extends Activity implements
		TencentLocationListener {
	private MapView mMapView;
	private LocationBean locationBean;
	private TencentLocationManager mLocationManager;
	private GeoPoint geoPoint;
	private PoiSearch poiSearch;
	private ListView poiListView;
	private PoiListAdapter poiListAdapter;
	private PoiResults poiResult;
	private List<PoiBean> listPoiBean = new ArrayList<PoiBean>();
	private List<PoiItem> listPoiItem = new ArrayList<PoiItem>();
	private Handler handler;
	private int selectedPositon = 1;
	private TencentLocation location;
	public static final int FROMLOCATION_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("locationbuddle");
		locationBean = (LocationBean) bundle.getSerializable("location");
		selectedPositon = locationBean.getIndex();
		if (selectedPositon <= 0) {
			selectedPositon = 1; // 如果传进来的值默认选了第0项。就改为第一项。
		}
		setContentView(R.layout.activity_location);
		poiSearch = new PoiSearch(this);
		mMapView = (MapView) findViewById(R.id.mapview);
		poiListView = (ListView) findViewById(R.id.poilistview);
		if (locationBean.getPoiLatitude() < 0) {
			mMapView.getController()
					.animateTo(
							of(locationBean.getLatitude(),
									locationBean.getLongitude()),
							new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub

								}
							});
		} else {
			mMapView.getController().animateTo(
					of(locationBean.getPoiLatitude(),
							locationBean.getPoiLongitude()), new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

						}
					});
		}
		mMapView.getController().setZoom(16);
		mLocationManager = TencentLocationManager.getInstance(this);
		geoPoint = of(locationBean.getLatitude(), locationBean.getLongitude());
		poiListAdapter = new PoiListAdapter(listPoiBean, LocationActivity.this);
		poiListView.setAdapter(poiListAdapter);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				poiListAdapter.setListPoiItem(listPoiBean);
				System.out.println("listPoiItem.size=" + listPoiBean.size());
				poiListAdapter.notifyDataSetChanged();
			}
		};
		new Thread() {
			@Override
			public void run() {
				try {
					poiResult = poiSearch.searchPoiInCircle("小区", geoPoint,
							1000);
					List<PoiItem> listPoiItem = poiResult
							.getCurrentPagePoiItems();
					if (listPoiBean != null) {
						listPoiBean.clear();
					}
					PoiBean poiBean = new PoiBean();
					poiBean.setGeoPoint(null);
					poiBean.setPoiAddress("");
					poiBean.setPoiName("不显示");
					listPoiBean.add(poiBean);
					for (PoiItem item : listPoiItem) {
						PoiBean bean = new PoiBean();
						bean.setPoiAddress(item.address);
						bean.setPoiName(item.name);
						bean.setGeoPoint(item.point);
						listPoiBean.add(bean);
					}
					listPoiItem.clear();
					if (locationBean.getIndex() <= 0 && listPoiBean.size() > 1) {
						listPoiBean.get(1).setSelected(true);
					} else {
						listPoiBean.get(locationBean.getIndex()).setSelected(
								true);
					}
					handler.sendEmptyMessage(0x11);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}.start();
		poiListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (position != 0) {
					mMapView.getController().animateTo(
							listPoiBean.get(position).getGeoPoint(),
							new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub

								}
							});
				}
				listPoiBean.get(selectedPositon).setSelected(false);
				listPoiBean.get(position).setSelected(true);
				poiListAdapter.setListPoiItem(listPoiBean);
				poiListAdapter.notifyDataSetChanged();
				selectedPositon = position;
			}
		});
		mMapView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GeoPoint mgeoPoint = mMapView.getMapCenter();
				if (mgeoPoint.getLatitudeE6() != geoPoint.getLatitudeE6()
						|| mgeoPoint.getLongitudeE6() != geoPoint
								.getLongitudeE6()) {
					geoPoint.setLatitudeE6(mgeoPoint.getLatitudeE6());
					geoPoint.setLongitudeE6(mgeoPoint.getLongitudeE6());
					listPoiBean.clear();
					PoiBean poiBean = new PoiBean();
					poiBean.setGeoPoint(null);
					poiBean.setPoiAddress("");
					poiBean.setPoiName("不显示");
					listPoiBean.add(poiBean);
					selectedPositon = 1;// 默认选择第一项
					new Thread() {
						@Override
						public void run() {
							try {
								poiResult = poiSearch.searchPoiInCircle("小区",
										geoPoint, 1000);
								listPoiItem.clear();
								listPoiItem = poiResult
										.getCurrentPagePoiItems();
								for (PoiItem item : listPoiItem) {
									PoiBean bean = new PoiBean();
									bean.setPoiAddress(item.address);
									bean.setPoiName(item.name);
									bean.setGeoPoint(item.point);
									listPoiBean.add(bean);
								}
								if (listPoiBean.size() > 1) {
									listPoiBean.get(selectedPositon)
											.setSelected(true);
								}
								handler.sendEmptyMessage(0x11);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}.start();

				}
			}
		});
	}

	@Override
	public void onLocationChanged(TencentLocation arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		location = arg0;
	}

	@Override
	public void onStatusUpdate(String arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	public LocationBean getLocationBean() {
		return locationBean;
	}

	public void setLocationBean(LocationBean locationBean) {
		this.locationBean = locationBean;
	}

	private static GeoPoint of(double latitude, double longitude) {
		GeoPoint ge = new GeoPoint((int) (latitude * 1E6),
				(int) (longitude * 1E6));
		return ge;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startLocation();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		stopLocation();
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mMapView.onDestroy();
		super.onDestroy();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mMapView.onPause();
		super.onPause();

	}

	private void startLocation() {
		TencentLocationRequest request = TencentLocationRequest.create();
		request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
		mLocationManager.requestLocationUpdates(request, this);
	}

	private void stopLocation() {
		mLocationManager.removeUpdates(this);
	}

	public void LocationOnclick(View v) {
		double thisLatitude = location.getLatitude();
		double thislongtitude = location.getLongitude();
		geoPoint = new GeoPoint((int) (thisLatitude * 1E6),
				(int) (thislongtitude * 1E6));
		mMapView.getController().animateTo(of(thisLatitude, thislongtitude),
				new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

					}
				});

		listPoiBean.clear();
		selectedPositon = 1;
		new Thread() {
			@Override
			public void run() {
				try {
					poiResult = poiSearch.searchPoiInCircle("小区", geoPoint,
							1000);
					listPoiItem.clear();
					PoiBean poiBean = new PoiBean();
					poiBean.setGeoPoint(null);
					poiBean.setPoiAddress("");
					poiBean.setPoiName("不显示");
					listPoiBean.add(poiBean);
					listPoiItem = poiResult.getCurrentPagePoiItems();
					for (PoiItem item : listPoiItem) {
						PoiBean bean = new PoiBean();
						bean.setPoiAddress(item.address);
						bean.setPoiName(item.name);
						bean.setGeoPoint(item.point);
						listPoiBean.add(bean);
					}
					if (listPoiBean.size() > 1) {
						listPoiBean.get(selectedPositon).setSelected(true);
					}
					handler.sendEmptyMessage(0x11);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			LocationBean locationBean = new LocationBean();
			if (listPoiBean.size() - 1 >= selectedPositon) {
				locationBean.setName(listPoiBean.get(selectedPositon)
						.getPoiName());
				if (listPoiBean.get(selectedPositon).getGeoPoint() != null) {
					locationBean
							.setPoiLatitude(listPoiBean.get(selectedPositon)
									.getGeoPoint().getLatitudeE6() / 1000000.0);
					locationBean.setPoiLongitude(listPoiBean
							.get(selectedPositon).getGeoPoint()
							.getLongitudeE6() / 1000000.0);
				} else {
					locationBean.setPoiLatitude(-1);
					locationBean.setPoiLongitude(-1);
				}
				locationBean.setLatitude(geoPoint.getLatitudeE6() / 1000000.0);
				locationBean
						.setLongitude(geoPoint.getLongitudeE6() / 1000000.0);
				locationBean.setIndex(selectedPositon);
				bundle.putSerializable("location", locationBean);
				intent.putExtra("locationbuddle", bundle);
				setResult(FROMLOCATION_CODE, intent);
			}
			finish();
		}
		return false;
	}

}
