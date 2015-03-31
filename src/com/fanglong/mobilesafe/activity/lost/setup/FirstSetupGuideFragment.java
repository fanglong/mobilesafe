package com.fanglong.mobilesafe.activity.lost.setup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseFragment;
import com.fanglong.mobilesafe.common.log.Logger;

/**
 * 第一步 - 
 * @author apple
 *
 */
public class FirstSetupGuideFragment extends BaseFragment implements View.OnClickListener{
	private int currentIndex;
	private OnSwitchChangeListener onNextChangeListener;
	
	public FirstSetupGuideFragment(int currentIndex) {
		super();
		this.currentIndex = currentIndex;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//设置View
		View view = inflater.inflate(R.layout.lostprotected_setupguide_fragment_first_content, null);
		view.findViewById(R.id.bt_next).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_next:
			Logger.debug("FirstSetupGuideFragment next ");
			//下一步
			if (onNextChangeListener != null) 
				onNextChangeListener.showNext(currentIndex);
			break;

		default:
			break;
		}
	}
	
	public void setOnNextChangeListener(
			OnSwitchChangeListener onNextChangeListener) {
		this.onNextChangeListener = onNextChangeListener;
	}
}
