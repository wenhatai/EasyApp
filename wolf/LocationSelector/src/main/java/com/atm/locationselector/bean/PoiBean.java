package com.atm.locationselector.bean;

import com.tencent.tencentmap.mapsdk.map.GeoPoint;
/**
 * 
 * created by limingzhang on 2014/8/10
 *
 */
public class PoiBean {
private String poiName;
private String poiAddress;
private GeoPoint geoPoint;
private boolean selected = false;
public String getPoiName() {
	return poiName;
}
public void setPoiName(String poiName) {
	this.poiName = poiName;
}
public String getPoiAddress() {
	return poiAddress;
}
public void setPoiAddress(String poiAddress) {
	this.poiAddress = poiAddress;
}
public boolean isSelected() {
	return selected;
}
public void setSelected(boolean selected) {
	this.selected = selected;
}
public GeoPoint getGeoPoint() {
	return geoPoint;
}
public void setGeoPoint(GeoPoint geoPoint) {
	this.geoPoint = geoPoint;
}


}
