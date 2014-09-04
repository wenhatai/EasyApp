package com.atm.locationselector.bean;

import java.io.Serializable;

public class LocationBean implements Serializable{
	/**
	 * 
	 * created by limingzhang on 2014/8/10
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private double latitude = -1.0;
	private double longitude= -1.0; 
	private double poiLatitude = -1.0; 
    private double poiLongitude = -1.0;
  
    private int    index  = 0;
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
    
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getPoiLatitude() {
		return poiLatitude;
	}

	public void setPoiLatitude(double poiLatitude) {
		this.poiLatitude = poiLatitude;
	}

	public double getPoiLongitude() {
		return poiLongitude;
	}

	public void setPoiLongitude(double poiLongitude) {
		this.poiLongitude = poiLongitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}
