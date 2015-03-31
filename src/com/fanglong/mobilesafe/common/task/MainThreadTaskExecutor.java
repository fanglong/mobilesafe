package com.fanglong.mobilesafe.common.task;


import android.os.Handler;
import android.os.Looper;

public class MainThreadTaskExecutor implements TaskExecutor {

    private final static Handler h = new Handler(Looper.getMainLooper());

    @Override
    public void post(Task task) {
        h.post(new TaskWrapper(task));
    }

    @Override
    public boolean postDelayed(Task task, long delayMillis) {
        return h.postDelayed(new TaskWrapper(task), delayMillis);
    }
}
