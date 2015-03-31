package com.fanglong.mobilesafe.entity.location;

public class LocationInfo {
	private double latiude;			//纬度
	private double longtitude;		//经度
	
	public LocationInfo(){
		
	}
	
	public LocationInfo(double latiude,double longtitude){
		this.latiude = latiude;
		this.longtitude = longtitude;
	}

	public double getLatiude() {
		return latiude;
	}

	public void setLatiude(double latiude) {
		this.latiude = latiude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
}
