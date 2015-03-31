package com.fanglong.mobilesafe.common.log;

public interface LogWriter {
    public void logMessage(long logTime, int line, String fileName, LogLevel level, String message);
}
