package com.fanglong.mobilesafe.common;

import java.util.Locale;

import com.fanglong.mobilesafe.application.MobileSafeApplication;
import com.fanglong.mobilesafe.common.log.Logger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {
	/** 非连接 */
	public final static int NO_CONNET = 0x00;
	/** 网络类型 - WIFI*/
	public final static int NETTYPE_WIFI = 0x01;
	/** 网络类型 - WAP*/
	public final static int NETTYPE_CMWAP = 0x02;
	/** 网络类型 - 手机上网*/
	public final static int NETTYPE_CMNET = 0x03;
	
	public final static String NetworkStatusChangeNotification = "NetworkStatusChangeNotification";
	
	private static NetworkStatus networkInfo = new NetworkStatus();
	
	private NetworkStatus() {

	}
	
	public static NetworkStatus getInstance(){
		return networkInfo;
	}
	
	private boolean connected = true;
	private int type;
	
	public synchronized int getType(){
		return type;
	}
	
	public synchronized void setType(int type){
		this.type = type;
	}
	
	public synchronized boolean isConnected(){
		if (!connected)
			checkConnected();
		return connected;
	}
	
	public void detect (Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork == null) {
			Logger.info("active network is null, current network status is disconnected");
			setConnected(false);
			setType(NO_CONNET);
		} else {
			boolean isConnected = activeNetwork.isConnected();
			if (!isConnected) {
				if (!activeNetwork.isConnectedOrConnecting()) {
					isConnected = false;
				}
			}
			
			int netType = getNetType(activeNetwork, isConnected);
			setType(netType);
			setConnected(isConnected);
		}
	}
	
	private synchronized void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	
	private void checkConnected(){
		ConnectivityManager cm = (ConnectivityManager)MobileSafeApplication.getGlobalContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo acInfo = cm.getActiveNetworkInfo();
		if (acInfo != null) {
			boolean isConnected = acInfo.isConnected();
			if (!isConnected) {
				if (!acInfo.isConnectedOrConnecting()){
					isConnected = false;
				}
			}
			
			int netType = getNetType(acInfo,isConnected);
			setType(netType);
			setConnected(isConnected);
		}
	}
	
	private int getNetType(NetworkInfo activeNetwork, boolean isConnected) {
		int netType = NO_CONNET;
		if (!isConnected) {
			return netType;
		}

		switch (activeNetwork.getType()) {
		case ConnectivityManager.TYPE_MOBILE:
			if (!StringUtils.isEmptyOrNull(activeNetwork.getExtraInfo())) {
				netType = activeNetwork.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet") ? NETTYPE_CMNET : NETTYPE_CMWAP;
			}
		case ConnectivityManager.TYPE_WIFI:
			netType = NETTYPE_WIFI;
			break;
		}
		return netType;
	}
}
