package com.fanglong.mobilesafe.common;

import android.content.Intent;

import com.fanglong.mobilesafe.application.MobileSafeApplication;
import com.fanglong.mobilesafe.common.log.Logger;

public class LogExceptionHandler implements Thread.UncaughtExceptionHandler {

	private final Thread.UncaughtExceptionHandler oldExceptionHandler;

	public LogExceptionHandler(Thread.UncaughtExceptionHandler handler) {
		this.oldExceptionHandler = handler;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		if (thread == null) {
			Logger.error("Uncaught exception happened, but thread is null");
			return;
		}
		if (throwable == null) {
			Logger.error("Uncaught exception happened in " + thread.getName() + ", but throwable is null");
			return;
		}
		Logger.error("Uncaught Exception in " + thread.getName() + "[" + thread.getId() + "]", throwable);
		//MobclickAgent.reportError(MobileSafeApplication.getGlobalContext(), throwable);
		if (oldExceptionHandler != null) {
			oldExceptionHandler.uncaughtException(thread, throwable);
		}
		// 出错时重启应用
		//MobclickAgent.onKillProcess(MobileSafeApplication.getGlobalContext());
		Intent intent = MobileSafeApplication.getGlobalContext().getPackageManager()
				.getLaunchIntentForPackage(MobileSafeApplication.getGlobalContext().getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		MobileSafeApplication.getGlobalContext().startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
