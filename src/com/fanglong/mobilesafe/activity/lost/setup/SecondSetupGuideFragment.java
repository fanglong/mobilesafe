package com.fanglong.mobilesafe.activity.lost.setup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseFragment;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.lost.ILostProtectedMgr;

/**
 * 第二步 - SIM绑定
 * @author apple
 *
 */
public class SecondSetupGuideFragment extends BaseFragment implements View.OnClickListener,OnCheckedChangeListener{
	private int currentIndex;
	private OnSwitchChangeListener onSwitchChangeListener;
	private CheckBox cbBind;
	private Button btBind;
	
	public SecondSetupGuideFragment(int currentIndex) {
		super();
		this.currentIndex = currentIndex;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lostprotected_setupguide_fragment_second_content, null);
		view.findViewById(R.id.bt_next).setOnClickListener(this);
		view.findViewById(R.id.bt_previous).setOnClickListener(this);
		btBind = (Button)view.findViewById(R.id.bt_bind);
		btBind.setOnClickListener(this);
		
		cbBind = ((CheckBox)view.findViewById(R.id.cb_bind));
		cbBind.setOnCheckedChangeListener(this);
		if (ServiceMgr.getService(ILostProtectedMgr.class).checkSimBind()){
			cbBind.setChecked(true);
		}
		
		return view;
	}
	
	public void setOnNextChangeListener(
			OnSwitchChangeListener onSwitchChangeListener) {
		this.onSwitchChangeListener = onSwitchChangeListener;
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//判断是否解除SIM卡绑定
		if (isChecked){
			//绑定
			cbBind.setText(R.string.txt_bind);
		} else {
			//进行解除绑定
			unBindSim();
			cbBind.setText(R.string.txt_unbind);
		}
	}
	
	private void unBindSim() {
		ServiceMgr.getService(ILostProtectedMgr.class).unBindSim();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_bind:
			bindSim();
			break;
		case R.id.bt_next:
			//未绑定 进行提示
			if (!ServiceMgr.getService(ILostProtectedMgr.class).checkSimBind()){
				showToastMessage(R.string.msg_toast_unbind_sim);
				return;
			}
			//现实下一步
			if (onSwitchChangeListener != null)
				onSwitchChangeListener.showNext(currentIndex);
			break;
		case R.id.bt_previous:
			if (onSwitchChangeListener != null)
				onSwitchChangeListener.showPrevious(currentIndex);
		default:
			break;
		}
	}

	private void bindSim() {
		//获取手机服务
		TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNumber = telephonyManager.getSimSerialNumber();
		//SMS卡绑定
		ServiceMgr.getService(ILostProtectedMgr.class).bindSim(simSerialNumber);
		cbBind.setChecked(true);
	}
}
