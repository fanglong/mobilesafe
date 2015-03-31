package com.fanglong.mobilesafe.common.log;

import java.util.Locale;

public class ConsoleLogFormatter implements LogFormatter {

	@Override
	public String format(long logTime, int line, String fileName, LogLevel level, String fmt, Object... args) {
		try {
			String msg = null;
			if (args != null && args.length > 0) {
				msg = String.format(fmt, args);
			} else {
				msg = fmt;
			}
			String finalMsg = String.format(Locale.getDefault(), "%s (%s:%d)", msg, fileName, line);
			return finalMsg;
		} catch (Exception ex) {
			return "log format error at com.up366.mobile.common.log.ConsoleLogFormatter (ConsoleLogFormatter.java:15)";
		}
	}
}
