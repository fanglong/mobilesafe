package com.fanglong.mobilesafe.activity.lost.setup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseFragment;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.lost.ILostProtectedMgr;
import com.fanglong.mobilesafe.common.log.Logger;
import com.fanglong.mobilesafe.receiver.advice.MobileSafeDeviceAdmin;

/**
 * 第四步 - 
 * @author apple
 *
 */
@SuppressLint("InflateParams")
public class FourthSetupGuideFragment extends BaseFragment implements View.OnClickListener,OnCheckedChangeListener{
	private int currentIndex;
	private OnSwitchChangeListener onSwitchChangeListener;
	private CheckBox cbIsProtecting;
	
	public FourthSetupGuideFragment(int currentIndex) {
		super();
		this.currentIndex = currentIndex;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lostprotected_setupguide_fragment_fourth_content, null);
		view.findViewById(R.id.bt_setup_finish).setOnClickListener(this);
		view.findViewById(R.id.bt_previous).setOnClickListener(this);
		cbIsProtecting = (CheckBox)view.findViewById(R.id.cb_isprotecting);
		cbIsProtecting.setOnCheckedChangeListener(this);
		//验证是否在保护中
		if (ServiceMgr.getService(ILostProtectedMgr.class).checkIsProtecting()){
			cbIsProtecting.setChecked(true);
			enableProtect();
		} else {
			cbIsProtecting.setChecked(false);
		}
		return view;
	}
	
	private void enableProtect() {
		showToastMessage(R.string.msg_toast_disable_protecting);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked){
			cbIsProtecting.setText(R.string.txt_isprotecting);
		} else {
			cbIsProtecting.setTag(R.string.txt_is_not_protecting);
		}
	}
	
	public void setOnNextChangeListener(
			OnSwitchChangeListener onSwitchChangeListener) {
		this.onSwitchChangeListener = onSwitchChangeListener;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_previous:
			if (onSwitchChangeListener != null)
				onSwitchChangeListener.showPrevious(currentIndex);
			break;
		case R.id.bt_setup_finish:
			//开启保护
			if (!cbIsProtecting.isChecked()){
				showToastMessage(R.string.msg_toast_disable_protecting);
				return;
			}
			//获取设备服务
			Activity parent = getActivity();
			DevicePolicyManager manager = (DevicePolicyManager)parent.getSystemService(Context.DEVICE_POLICY_SERVICE);
			//创建组件
			ComponentName componentName = new ComponentName(parent,MobileSafeDeviceAdmin.class);
			//注册组件
			if (!manager.isAdminActive(componentName)){
				Logger.debug("完成设置，激活设备管理器");
				//不是管理设备 进行注册
				Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
				startActivity(intent);
			}
			if (onSwitchChangeListener != null)
				onSwitchChangeListener.showNext(currentIndex);
			break;
		default:
			break;
		}
	}
}
