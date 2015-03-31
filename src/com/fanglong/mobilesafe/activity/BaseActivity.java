package com.fanglong.mobilesafe.activity;


import android.app.Activity;
import android.widget.Toast;

import com.fanglong.mobilesafe.common.NetworkStatus;
import com.fanglong.mobilesafe.common.task.Task;
import com.fanglong.mobilesafe.common.task.TaskUtils;

/**
 * 基础Activity
 * @author apple
 *
 */
public class BaseActivity extends Activity{
	
	/**
	 * Toast 现实消息信息
	 * @param msg
	 */
	protected void showToastMessage(final String msg) {
		if (TaskUtils.isMainThread()) {
			showMes(msg);
		} else {
			TaskUtils.getMainExecutor().post(new Task() {
				@Override
				public void run() throws Exception {
					showMes(msg);
				}
			});
		}
	}
	
	private Toast t = null;

	private void showMes(String msg) {
		if(t==null){
			 t= Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
		}
		t.setText(msg);
		t.show();
	}
	
	private void showMes(int resId) {
		if(t==null){
			 t= Toast.makeText(getBaseContext(), resId, Toast.LENGTH_SHORT);
		}
		t.setText(resId);
		t.show();
	}

	protected void showToastMessage(final int resId) {
		if (TaskUtils.isMainThread()) {
			showMes(resId);
		} else {
			TaskUtils.getMainExecutor().post(new Task() {
				@Override
				public void run() throws Exception {
					showMes(resId);
				}
			});
		}
	}
	
	/**
	 * 判断网络链接状态
	 * @return	链接返回 true 否则返回false
	 */
	protected boolean isNetworkDisconnected() {
		return !NetworkStatus.getInstance().isConnected();
	}
}
