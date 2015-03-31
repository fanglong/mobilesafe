package com.fanglong.mobilesafe.business.version;

import com.fanglong.mobilesafe.application.MobileSafeApplication;

import android.content.Context;
import android.content.SharedPreferences;


public class UninstallClean {
	private final static String FIRST_INSTALL = "com.fanglong.mobile.install";
	private final static String HasInstall = "hasinstall";
	
	public static void updateInstallState() {
		Context context = MobileSafeApplication.getGlobalContext();
		SharedPreferences setting = context.getSharedPreferences(FIRST_INSTALL, Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = setting.edit();
		edit.putBoolean(HasInstall, true);
		edit.commit();
	}
	public static boolean getInstallState() {
		SharedPreferences setting = MobileSafeApplication.getGlobalContext().getSharedPreferences(FIRST_INSTALL, Context.MODE_PRIVATE);
		return setting.getBoolean(HasInstall, false);
	}
}
