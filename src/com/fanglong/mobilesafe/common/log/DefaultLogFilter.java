package com.fanglong.mobilesafe.common.log;

public class DefaultLogFilter implements LogFilter {

    private final LogLevel minLogLevel;

    public DefaultLogFilter(LogLevel minLogLevel) {
        this.minLogLevel = minLogLevel;
    }

    @Override
    public boolean allowLogMessage(LogLevel level, String message) {
        if (minLogLevel.ordinal() <= level.ordinal()) {
            return true;
        } else {
            return false;
        }
    }
}
