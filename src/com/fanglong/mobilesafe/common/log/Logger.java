package com.fanglong.mobilesafe.common.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {

    public final static String TAG = "MobileSafe";
    public final static String LOG_RELATIVE_PATH = "/MobileSafe/Logs";
    public final static LogLevel LOG_LEVEL = LogLevel.getLogLevel();

    private static LogUtils gLogUtils = null;

    private static synchronized LogUtils getInstance() {
        if (gLogUtils == null) {
            ConsoleLogWriter consoleLogWriter = new ConsoleLogWriter(TAG, new ConsoleLogFormatter());
            FileLogWriter fileLogWriter = new FileLogWriter(new FileLogFormatter(), LOG_RELATIVE_PATH);
            CompositorLogWriter writer = new CompositorLogWriter();
            writer.addLogWriter(consoleLogWriter);
            writer.addLogWriter(fileLogWriter);
            gLogUtils = new LogUtils(writer, new DefaultLogFilter(LOG_LEVEL));
            gLogUtils.info("-----------application started-----------");
        }
        return gLogUtils;
    }

    public static synchronized void setInstance(LogUtils logUtils) {
        gLogUtils = logUtils;
    }

    public static void verbose(String paramString) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelVerbose.ordinal()) {
            return;
        }
        if (paramString == null) {
            return;
        }
        getInstance().verbose(paramString);
    }

    public static void debug(String paramString) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelDebug.ordinal()) {
            return;
        }
        if (paramString == null) {
            return;
        }
        getInstance().debug(paramString);
    }

    public static void debug(String paramString, Throwable paramThrowable) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelDebug.ordinal()) {
            return;
        }
        if (paramString == null || paramThrowable == null) {
            return;
        }
        String msg = combineLogMessageWithException(paramString, paramThrowable);
        getInstance().debug(msg);
    }

    public static void info(String paramString) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelInfo.ordinal()) {
            return;
        }
        if (paramString == null) {
            return;
        }
        getInstance().info(paramString);
    }

    public static void info(String paramString, Throwable paramThrowable) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelInfo.ordinal()) {
            return;
        }
        if (paramString == null || paramThrowable == null) {
            return;
        }
        String msg = combineLogMessageWithException(paramString, paramThrowable);
        getInstance().info(msg);
    }

    public static void warn(String paramString) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelWarn.ordinal()) {
            return;
        }
        if (paramString == null) {
            return;
        }
        getInstance().warn(paramString);
    }

    public static void warn(String paramString, Throwable paramThrowable) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelWarn.ordinal()) {
            return;
        }
        if (paramString == null || paramThrowable == null) {
            return;
        }
        String msg = combineLogMessageWithException(paramString, paramThrowable);
        getInstance().warn(msg);
    }

    public static void warn(Throwable paramThrowable) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelWarn.ordinal()) {
            return;
        }
        if (paramThrowable == null) {
            return;
        }
        String msg = combineLogMessageWithException(paramThrowable.getMessage(), paramThrowable);
        getInstance().warn(msg);
    }

    public static void error(String paramString) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelError.ordinal()) {
            return;
        }
        if (paramString == null) {
            return;
        }
        getInstance().error(paramString);
    }

    public static void error(Throwable paramThrowable) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelError.ordinal()) {
            return;
        }
        if (paramThrowable == null) {
            return;
        }
        String msg = combineLogMessageWithException(paramThrowable.getMessage(), paramThrowable);
        getInstance().error(msg);
    }

    public static void error(String paramString, Throwable paramThrowable) {
        if (LOG_LEVEL.ordinal() > LogLevel.LogLevelError.ordinal()) {
            return;
        }
        if (paramString == null && paramThrowable == null) {
            return;
        }
        String msg = combineLogMessageWithException(paramString, paramThrowable);
        getInstance().error(msg);
    }

    private static String combineLogMessageWithException(String message, Throwable t) {
        StringWriter sw = new StringWriter();
        sw.write(message);
        sw.write("\n");
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
