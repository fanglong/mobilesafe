package com.fanglong.mobilesafe.common.log;

import android.util.Log;

public class SimpleLogger {
    public final static int logLevel = 1;
    public final static String TAG = "Up366Mobile";

    public static void verbose(String paramString) {
        if ((logLevel <= 1) && (paramString != null)) {
            Log.v(TAG, paramString);
        }
    }

    public static void debug(String paramString) {
        if ((logLevel <= 2) && (paramString != null)) {
            Log.d(TAG, paramString);
        }
    }

    public static void debug(String paramString, Throwable paramThrowable) {
        if ((logLevel <= 2) && (paramString != null) && (paramThrowable != null)) {
            Log.d(TAG, paramString, paramThrowable);
        }
    }

    public static void info(String paramString) {
        if ((logLevel <= 3) && (paramString != null)) {
            Log.i(TAG, paramString);
        }
    }

    public static void info(String paramString, Throwable paramThrowable) {
        if ((logLevel <= 3) && (paramString != null) && (paramThrowable != null)) {
            Log.i(TAG, paramString, paramThrowable);
        }
    }

    public static void warn(String paramString) {
        if ((logLevel <= 4) && (paramString != null)) {
            Log.w(TAG, paramString);
        }
    }

    public static void warn(String paramString, Throwable paramThrowable) {
        if ((logLevel <= 4) && (paramString != null) && (paramThrowable != null)) {
            Log.w(TAG, paramString, paramThrowable);
        }
    }

    public static void warn(Throwable paramThrowable) {
        if ((logLevel <= 4) && (paramThrowable != null)) {
            Log.w(TAG, paramThrowable);
        }
    }

    public static void error(String paramString) {
        if ((logLevel <= 5) && (paramString != null)) {
            Log.e(TAG, paramString);
        }
    }

    public static void error(Throwable paramThrowable) {
        if ((logLevel <= 5) && (paramThrowable != null)) {
            Log.e(TAG, "", paramThrowable);
        }
    }

    public static void error(String paramString, Throwable paramThrowable) {
        if ((logLevel <= 5) && (paramString != null) && (paramThrowable != null)) {
            Log.e(TAG, paramString, paramThrowable);
        }
    }
}
