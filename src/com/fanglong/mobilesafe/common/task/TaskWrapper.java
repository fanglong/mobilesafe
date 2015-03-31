package com.fanglong.mobilesafe.common.task;

import com.fanglong.mobilesafe.common.log.Logger;



public class TaskWrapper implements Runnable {

	private final Task task;

	public TaskWrapper(Task task) {
		this.task = task;
	}

	@Override
	public void run() {
		try {
			task.run();
		} catch (Exception ex) {
			// Logger.error(ex);
			Logger.error("msg:" + ex.getMessage(), ex);
		}
	}
}
