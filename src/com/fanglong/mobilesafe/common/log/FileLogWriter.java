package com.fanglong.mobilesafe.common.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.text.format.Time;

import com.fanglong.mobilesafe.common.FileUtils;
import com.fanglong.mobilesafe.common.StringUtils;
import com.fanglong.mobilesafe.common.task.Task;
import com.fanglong.mobilesafe.common.task.TaskExecutor;
import com.fanglong.mobilesafe.common.task.TaskUtils;

public class FileLogWriter implements LogWriter {

    private final static String kLogFileSuffix = ".log";
    /** 10 days. */
    private static final long kLogFileAliveDays = 10L * 24 * 60 * 60 * 1000;

    private final LogFormatter logFormatter;
    private final String relativePath;
    private final TaskExecutor executor = TaskUtils.createSerialExecutor("Logger");
    private File logFile = null;
    private String logFileName = null;

    public FileLogWriter(LogFormatter logFormatter, String relativePath) {
        this.logFormatter = logFormatter;
        this.relativePath = relativePath;
        this.cleanOldFiles();
    }

    private String getLogFileName() {
        Time time = new Time();
        time.setToNow();
        return time.format("%Y-%m-%d") + kLogFileSuffix;
    }

    private String getLogFilePath() {
        String logDir = FileUtils.combineFilePath(FileUtils.getExternalStorageDir(), relativePath);
        return FileUtils.combineFilePath(logDir, logFileName);
    }

    private void createLogFile() throws IOException {
        logFileName = getLogFileName();
        String logFilePath = getLogFilePath();
        logFile = new File(logFilePath);
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
    }

    private void cleanOldFiles() {
        executor.post(new Task() {
            @Override
            public void run() throws Exception {
                String logDir = FileUtils.combineFilePath(FileUtils.getExternalStorageDir(), relativePath);
                File dir = new File(logDir);
                if (!dir.exists()) {
                    return;
                }
                long now = System.currentTimeMillis();
                File files[] = dir.listFiles();
                if (files == null) {
                    return;
                }
                for (File file : files) {
                    long lastModifiedTime = file.lastModified();
                    if ((now - lastModifiedTime) > kLogFileAliveDays) {
                        file.delete();
                    }
                }
            }
        });
    }

    @Override
    public void logMessage(long logTime, int line, String fileName, LogLevel level, String message) {
        final String msg = logFormatter.format(logTime, line, fileName, level, message);
        if (msg != null && msg.length() > 0) {
            executor.post(new Task() {
                @Override
                public void run() throws Exception {
                    try {
                        if (!FileUtils.storageIsWritable()) {
                            return;
                        }
                        if (!FileUtils.makeDirUnderExternalStorage(relativePath)) {
                            return;
                        }
                        if (logFile == null) {
                            createLogFile();
                        } else {
                            String fileName = getLogFileName();
                            if (!StringUtils.isEqualCaseInsensitive(fileName, logFileName)) {
                                createLogFile();
                            }
                        }
                        if (logFile == null) {
                            return;
                        }
                        BufferedWriter writer = null;
                        try {
                            FileWriter fileWriter = new FileWriter(logFile, true);
                            writer = new BufferedWriter(fileWriter);
                            writer.write(msg + "\n");
                            writer.flush();
                        } finally {
                            if (writer != null) {
                                writer.close();
                            }
                        }
                    } catch (Exception ex) {
                    	logFile.delete();
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
}
