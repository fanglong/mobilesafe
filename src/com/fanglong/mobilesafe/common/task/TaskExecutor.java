package com.fanglong.mobilesafe.common.task;

public interface TaskExecutor {
    public void post(Task task);
    public boolean postDelayed(Task task, long delayMillis);
}
