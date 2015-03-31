package com.fanglong.mobilesafe.common.log;

public interface LogFilter {
    public boolean allowLogMessage(LogLevel level, String message);
}
