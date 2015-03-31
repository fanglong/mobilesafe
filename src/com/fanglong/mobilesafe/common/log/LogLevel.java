package com.fanglong.mobilesafe.common.log;

import com.fanglong.mobilesafe.LoadConfig;


public enum LogLevel {
	LogLevelVerbose, LogLevelDebug, LogLevelInfo, LogLevelWarn, LogLevelError;

	public static LogLevel getLogLevel() {
		return getLogLevelByKey(LoadConfig.getInstance().loadDataFromConfigForInt(LoadConfig.KEY_LOG_LEVEL, 5));
	}

	private static LogLevel getLogLevelByKey(int key) {
		switch (key) {
		case 1:
			return LogLevelVerbose;
		case 2:
			return LogLevelDebug;
		case 3:
			return LogLevelInfo;
		case 4:
			return LogLevelWarn;
		}

		return LogLevelError;
	}
}
