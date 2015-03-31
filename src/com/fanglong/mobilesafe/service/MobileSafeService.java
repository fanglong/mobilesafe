package com.fanglong.mobilesafe.service;

import com.fanglong.mobilesafe.common.NetworkStatus;
import com.fanglong.mobilesafe.common.log.Logger;
import com.fanglong.mobilesafe.common.notification.NotificationCenter;
import com.fanglong.mobilesafe.common.notification.NotificationHandler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MobileSafeService extends Service{
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		 super.onStartCommand(intent, flags, startId);
		 NotificationCenter.scanHandlers(this);
		 Logger.info("MobileService was started , onStartCommand() will return "+ START_STICKY);
		
		 NetworkStatus.getInstance().detect(this);
		 
		 return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		NotificationCenter.clearHandlers(this);
		super.onDestroy();
	}
	
	@NotificationHandler(name=NetworkStatus.NetworkStatusChangeNotification)
	private void handleNetworkChange (Intent notification){
		if (NetworkStatus.getInstance().isConnected() == false) {
			//networkStatus = true;
		}
		// 网络连接中 - 处理业务代码
		if (NetworkStatus.getInstance().isConnected()) {

		}
	}
}
