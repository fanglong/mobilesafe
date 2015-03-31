package com.fanglong.mobilesafe.common.log;

import android.util.Log;

public class LogUtils {

    private final LogWriter logWriter;
    private final LogFilter logFilter;

    public LogUtils(LogWriter logWriter, LogFilter logFilter) {
        this.logWriter = logWriter;
        this.logFilter = logFilter;
    }

    public void verbose(String fmt, Object... args) {
        internalLogMessage(System.currentTimeMillis(), LogLevel.LogLevelVerbose, fmt, args);
    }

    public void debug(String fmt, Object... args) {
        internalLogMessage(System.currentTimeMillis(), LogLevel.LogLevelDebug, fmt, args);
    }

    public void info(String fmt, Object... args) {
        internalLogMessage(System.currentTimeMillis(), LogLevel.LogLevelInfo, fmt, args);
    }

    public void warn(String fmt, Object... args) {
        internalLogMessage(System.currentTimeMillis(), LogLevel.LogLevelWarn, fmt, args);
    }

    public void error(String fmt, Object... args) {
        internalLogMessage(System.currentTimeMillis(), LogLevel.LogLevelError, fmt, args);
    }

    private void internalLogMessage(long logTime, LogLevel level, String fmt, Object... args) {
        try {
            String msg = null;
            if(args != null && args.length > 0){msg = String.format(fmt, args);}else{msg = fmt;}
            if (msg.length() > 0 && logFilter.allowLogMessage(level, msg)) {
                int line = getCallerLineNumber();
                String fileName = getCallerFilename();
                logWriter.logMessage(logTime, line, fileName, level, msg);
            }
        } catch (Exception ex) {
            Log.e("LogUtils", ex.getMessage(), ex);
        }
    }

    private int getCallerLineNumber() {
        return Thread.currentThread().getStackTrace()[6].getLineNumber();
    }

    private String getCallerFilename() {
        return Thread.currentThread().getStackTrace()[6].getFileName();
    }
}
