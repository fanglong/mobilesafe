package com.fanglong.mobilesafe.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fanglong.mobilesafe.common.log.Logger;


public class PackageUtils {

    public static int kDefaultVersionCode = 1;
    public static String kDefaultVersionName = "";

    public static int getVersionCode(Context context) {
        PackageManager mgr = context.getPackageManager();
        try {
            PackageInfo packageInfo = mgr.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.error("failed to get package version code");
            return kDefaultVersionCode;
        }
    }

    public static String getVersionName(Context context) {
        PackageManager mgr = context.getPackageManager();
        try {
            PackageInfo packageInfo = mgr.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.error("failed to get package version name");
            return kDefaultVersionName;
        }
    }
}
