package com.fanglong.mobilesafe.common.task;

import android.os.Looper;

public class TaskUtils {

    private final static MainThreadTaskExecutor gMainThreadTaskExecutor = new MainThreadTaskExecutor();
    private final static ConcurrentTaskExecutor gConcurrentTaskExecutor = new ConcurrentTaskExecutor("GlobalConcurrent");

    public static TaskExecutor getMainExecutor() {
        return gMainThreadTaskExecutor;
    }

    public static TaskExecutor getGlobalExecutor() {
        return gConcurrentTaskExecutor;
    }

    public static TaskExecutor createSerialExecutor(String threadPrefix) {
        return new SerialTaskExecutor(threadPrefix);
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
