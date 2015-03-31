package com.fanglong.mobilesafe.common.log;

public interface LogFormatter {
    public String format(long logTime, int line, String fileName, LogLevel level, String fmt, Object... args);
}
