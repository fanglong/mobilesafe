package com.fanglong.mobilesafe.common.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FileLogFormatter implements LogFormatter {

	private final static Map<LogLevel, String> gLogLevelStrMap = new HashMap<LogLevel, String>();

	static {
		gLogLevelStrMap.put(LogLevel.LogLevelVerbose, "VERB");
		gLogLevelStrMap.put(LogLevel.LogLevelDebug, "DEBUG");
		gLogLevelStrMap.put(LogLevel.LogLevelInfo, "INFO");
		gLogLevelStrMap.put(LogLevel.LogLevelWarn, "WARN");
		gLogLevelStrMap.put(LogLevel.LogLevelError, "ERROR");
	}

	private static String getLogLevelStr(LogLevel level) {
		if (gLogLevelStrMap.containsKey(level)) {
			return gLogLevelStrMap.get(level);
		} else {
			return "";
		}
	}

	private final static String kDateFormat = "yy-MM-dd HH:mm:ss.SSS";

	private final static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(kDateFormat, Locale.getDefault());
		}
	};

	@Override
	public String format(long logTime, int line, String fileName, LogLevel level, String fmt, Object... args) {
		try {
			String levelStr = getLogLevelStr(level);
			String msg = null;
			if (args != null && args.length > 0) {
				msg = String.format(fmt, args);
			} else {
				msg = fmt;
			}
			String now = dateFormat.get().format(new Date(logTime));
			String finalMsg = String.format(Locale.getDefault(), "%s [0x%08x] [%-5s] - %s (%s:%d)", now, Thread.currentThread().getId(), levelStr, msg, fileName, line);
			return finalMsg;
		} catch (Exception ex) {
			return "log format error at com.up366.mobile.common.log.FileLogFormatter (FileLogFormatter.java:53)";
		}
	}
}
