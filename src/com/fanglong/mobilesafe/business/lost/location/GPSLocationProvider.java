package com.fanglong.mobilesafe.business.lost.location;

import com.fanglong.mobilesafe.entity.location.LocationInfo;

public class GPSLocationProvider {
	private static OnLocationChangeListener locationChangeListener = new OnLocationChangeListener() {
		private LocationInfo info;
		@Override
		public void onLocationChange(LocationInfo info) {
			this.info = info;
		}
		
		@Override
		public LocationInfo getLocationInfo() {
			return info;
		}
	};
	
	public static OnLocationChangeListener getLocationChangeListener(){
		return locationChangeListener;
	}
}
