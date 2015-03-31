package com.fanglong.mobilesafe.common.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fanglong.mobilesafe.common.log.Logger;


public class SerialTaskExecutor implements TaskExecutor {

    private final ExecutorService executorService;

    public SerialTaskExecutor(String threadPrefix) {
        executorService = Executors.newSingleThreadExecutor(TaskThreadFactoryBuilder.newThreadFactory(threadPrefix));
    }

    @Override
    public void post(Task task) {
        executorService.submit(new TaskWrapper(task));
    }

    @Override
    public boolean postDelayed(Task task, long delayMillis) {
        Logger.error("postDelayed is not support in SerialTaskExecutor");
        return false;
    }
}
