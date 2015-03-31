package com.fanglong.mobilesafe.common.log;

import java.util.ArrayList;
import java.util.List;

public class CompositorLogWriter implements LogWriter {

    private final List<LogWriter> logWriters;

    public CompositorLogWriter() {
        logWriters = new ArrayList<LogWriter>();
    }

    public void addLogWriter(LogWriter logWriter) {
        if (!logWriters.contains(logWriter)) {
            logWriters.add(logWriter);
        }
    }

    @Override
    public void logMessage(long logTime, int line, String fileName,  LogLevel level, String message) {
        for (LogWriter writer : logWriters) {
            writer.logMessage(logTime, line, fileName, level, message);
        }
    }
}
