package com.fanglong.mobilesafe.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import com.fanglong.mobilesafe.common.FileHelper;
import com.fanglong.mobilesafe.common.LogExceptionHandler;
import com.fanglong.mobilesafe.common.log.Logger;
import com.fanglong.mobilesafe.common.notification.NotificationCenter;


public class MobileSafeApplication extends Application{
	private static Context gContext;		//上下文
	private static int gBatterPercent = 0;	//电量百分比
	private SystemInfoReceiver systemInfoReceiver = null;	//系统信息广播接受者
	
	@SuppressWarnings("static-access")
	@Override
	public void onCreate() {
		super.onCreate();
		gContext = this;
		final float density = this.getResources().getDisplayMetrics().density;
		Logger.debug("current device density is " + density);
		
		//获取当前主线程  设置线程无法捕捉异常处理
		Thread mainThread = Thread.currentThread();	
		LogExceptionHandler exceptionHandler = new LogExceptionHandler(mainThread.getUncaughtExceptionHandler());
		mainThread.setDefaultUncaughtExceptionHandler(exceptionHandler);
		
		//注册广播接受者 - 监听电量 以及 WIFI 变化广播
		this.systemInfoReceiver = new SystemInfoReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);		//监听过滤 电量变化
		intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);	//监听过滤 WIFI状态变化
		this.registerReceiver(systemInfoReceiver, intentFilter);
		
		FileHelper.createNomediaFileUnderMain();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
	}
	
	//实现广播接收
	class SystemInfoReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {	//电量改变
				int level = intent.getIntExtra("level", 0);
				int scale = intent.getIntExtra("scale", 0);
				gBatterPercent = level * 100 / scale;
				NotificationCenter.post(ApplicationNotification.BatteryInfoChanged, null);
			} else if (WifiManager.RSSI_CHANGED_ACTION.equals(intent.getAction())) {	//WIFI状态改变
				NotificationCenter.post(ApplicationNotification.WifiStrengthChanged, null);
			}
		}
	}

	public static int getgBatterPercent() {
		return gBatterPercent;
	}

	public static Context getGlobalContext() {
		return gContext;
	}
}
