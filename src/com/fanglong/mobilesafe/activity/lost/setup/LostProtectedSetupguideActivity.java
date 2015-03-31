package com.fanglong.mobilesafe.activity.lost.setup;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.lost.ILostProtectedMgr;
import com.fanglong.mobilesafe.common.log.Logger;

public class LostProtectedSetupguideActivity extends FragmentActivity implements OnSwitchChangeListener{
	
	List<Fragment> fragments = new ArrayList<Fragment>();
	
	//当前选Fragment
	private Fragment currFragment;
	public LostProtectedSetupguideActivity() {
		super();
		FirstSetupGuideFragment firstSetupGuideFragment = new FirstSetupGuideFragment(0);
		firstSetupGuideFragment.setOnNextChangeListener(this);
		SecondSetupGuideFragment secondSetupGuideFragment = new SecondSetupGuideFragment(1);
		secondSetupGuideFragment.setOnNextChangeListener(this);
		ThirdSetupGuideFragment thirdSetupGuideFragment = new ThirdSetupGuideFragment(2);
		thirdSetupGuideFragment.setOnNextChangeListener(this);
		FourthSetupGuideFragment fourthSetupGuideFragment = new FourthSetupGuideFragment(3);
		fourthSetupGuideFragment.setOnNextChangeListener(this);
		
		fragments.add(firstSetupGuideFragment);
		fragments.add(secondSetupGuideFragment);
		fragments.add(thirdSetupGuideFragment);
		fragments.add(fourthSetupGuideFragment);
		
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.lostprotected_setupguide_activity_content);
		Logger.debug("LostProtectedSetupguideActivity onCreate");
		//初始化
		initFragments(0);
	}

	private void initFragments(int index) {
		switchFragment(fragments.get(index));
	}
	
	private void switchFragment(Fragment switchFragment){
		if (switchFragment == null){
			return ;
		}
		//触发当前
		if (currFragment == switchFragment){
			return;
		}
		currFragment = switchFragment;
		try{
			try{
				getSupportFragmentManager().popBackStackImmediate();
			} catch (Exception e){
				Logger.error(e.getMessage());
			}
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.lostportected_setupguide_content, switchFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commitAllowingStateLoss();
		}catch(Exception e){
			Logger.error(e.getMessage());
		}
	}
	
	@Override
	public boolean showNext(int currentIndex) {
		Logger.debug("LostProtectedSetupguideActivity showNext currentIndex : " +currentIndex);
		if (currentIndex == fragments.size() - 1){
			//设置完成
			ServiceMgr.getService(ILostProtectedMgr.class).finishSetupGuide();
			finish();
			return true;
		}
		switchFragment(fragments.get(currentIndex + 1));
		return true;
	}
	
	@Override
	public boolean showPrevious(int currentIndex) {
		Logger.debug("LostProtectedSetupguideActivity showPrevious currentIndex : "+currentIndex);
		if (currentIndex == 0){
			return true;
		}
		switchFragment(fragments.get(currentIndex - 1));
		return true;
	}
}
