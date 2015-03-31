package com.fanglong.mobilesafe.business.lost.location;

import com.fanglong.mobilesafe.entity.location.LocationInfo;

public interface OnLocationChangeListener {
	public void onLocationChange(LocationInfo info);
	
	public LocationInfo getLocationInfo();
}
