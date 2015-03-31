package com.fanglong.mobilesafe.common.log;

import android.util.Log;

public class ConsoleLogWriter implements LogWriter {

    private final String tag;
    private final LogFormatter logFormatter;

    public ConsoleLogWriter(String tag, LogFormatter logFormatter) {
        this.tag = tag;
        this.logFormatter = logFormatter;
    }

    @Override
    public void logMessage(long logTime, int line, String fileName, LogLevel level, String message) {
        String finalMessage = logFormatter.format(logTime, line, fileName, level, message);
        switch (level) {
            case LogLevelVerbose:
                Log.v(tag, finalMessage);
                break;
            case LogLevelDebug:
                Log.d(tag, finalMessage);
                break;
            case LogLevelInfo:
                Log.i(tag, finalMessage);
                break;
            case LogLevelWarn:
                Log.w(tag, finalMessage);
                break;
            case LogLevelError:
                Log.e(tag, finalMessage);
                break;
            default:
                Log.v(tag, finalMessage);
                break;
        }
    }
}
