package com.fanglong.mobilesafe.activity;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.fanglong.mobilesafe.common.task.Task;
import com.fanglong.mobilesafe.common.task.TaskUtils;

public class BaseFragment extends Fragment{
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
		if (t == null) {
			t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
		}
		t.setText(msg);
		t.show();
	}

	private void showMes(int resId) {
		if (t == null) {
			t = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
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
}
