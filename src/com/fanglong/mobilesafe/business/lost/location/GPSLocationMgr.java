package com.fanglong.mobilesafe.business.lost.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.fanglong.mobilesafe.application.MobileSafeApplication;
import com.fanglong.mobilesafe.business.BaseMgr;
import com.fanglong.mobilesafe.business.ServiceFactory;
import com.fanglong.mobilesafe.entity.location.LocationInfo;

public class GPSLocationMgr extends BaseMgr implements IGPSLocationMgr {

	public GPSLocationMgr(ServiceFactory factory) {
		super(factory);
	}

	@Override
	public String getLocation(final OnLocationChangeListener locationListener) {
		LocationManager locationManager = (LocationManager)MobileSafeApplication.getGlobalContext().getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				getLocationProvider(locationManager)
				, 60000
				, 50
				, new LocationListener() {
					/**
					 * 当位置发生改变的时候调用
					 */
					@Override
					public void onLocationChanged(Location location) {
						LocationInfo info = new LocationInfo(location.getLatitude(),location.getLongitude());
						locationListener.onLocationChange(info);
					}
					/**
					 * 某一个设备的状态发生改变的时候调用 可用 转 不可用 | 不可用 转 可用
					 */
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
					}
					
					/**
					 * 某个设备被打开
					 */
					@Override
					public void onProviderEnabled(String provider) {
						
					}
					
					/**
					 * 某个设备被禁用
					 */
					@Override
					public void onProviderDisabled(String provider) {
					}
					
				});
		LocationInfo info = locationListener.getLocationInfo();
		if (info == null){
			return "";
		}
		return String.format("纬度: %s,经度: %s", info.getLatiude(),info.getLongtitude());
	}
	
	private String getLocationProvider(LocationManager manager){
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);			//精准度
		criteria.setAltitudeRequired(true); 					//海拔信息
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM); 	//耗电量
		criteria.setSpeedRequired(true); 						//速度敏感
		criteria.setCostAllowed(true); 							//允许费用开销
		return manager.getBestProvider(criteria, true);
	}
	
	
}
