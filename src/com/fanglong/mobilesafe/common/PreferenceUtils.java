package com.fanglong.mobilesafe.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.fanglong.mobilesafe.application.MobileSafeApplication;

public class PreferenceUtils {
	private static final String GLOBLE_SETTING = "app_settings";
	
	public static void putString(String key , String value){
		getShare().edit().putString(key, value).commit();
	}
	
	public static String getString(String key,String defValue){
		return getShare().getString(key, defValue);
	}
	
	public static void putInt(String key,int value){
		getShare().edit().putInt(key, value).commit();
	}
	
	public static int getInt(String key,int defValue){
		return getShare().getInt(key, defValue);
	}
	
	public static void putLong(String key, long value) {
		getShare().edit().putLong(key, value).commit();
	}

	public static long getLong(String key, long defValue) {
		return getShare().getLong(key, defValue);
	}

	public static void putBoolean(String key, Boolean value) {
		getShare().edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(String key, Boolean defValue) {
		return getShare().getBoolean(key, defValue);
	}
	
	private static synchronized SharedPreferences getShare(){
		return MobileSafeApplication.getGlobalContext().getSharedPreferences(GLOBLE_SETTING, Context.MODE_PRIVATE);
	}
}
