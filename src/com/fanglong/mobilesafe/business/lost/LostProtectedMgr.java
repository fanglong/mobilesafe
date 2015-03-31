package com.fanglong.mobilesafe.business.lost;

import com.fanglong.mobilesafe.business.BaseMgr;
import com.fanglong.mobilesafe.business.NFC;
import com.fanglong.mobilesafe.business.ServiceFactory;
import com.fanglong.mobilesafe.common.PreferenceUtils;
import com.fanglong.mobilesafe.common.StringUtils;
import com.fanglong.mobilesafe.common.digest.MD5;
import com.fanglong.mobilesafe.common.notification.NotificationCenter;

/**
 * 手机防盗业务类
 * @author apple
 *
 */
public class LostProtectedMgr extends BaseMgr implements ILostProtectedMgr {

	public LostProtectedMgr(ServiceFactory factory) {
		super(factory);
	}

	@Override
	public void checkPasswordSetup() {
		String password = PreferenceUtils.getString(NFC.LostProtectedNotifiations.LostProtectedPwdKey, "");
		//验证密码是否设置
		if (StringUtils.isEmptyOrNull(password)){
			NotificationCenter.post(NFC.LostProtectedNotifiations.LostProtectedPwdNotSetUpNotification, null);
		} else {
			NotificationCenter.post(NFC.LostProtectedNotifiations.LostProtectedPwdSetupedNotification, null);
		}
	}

	@Override
	public boolean updatePwd(String pwd) {
		//进行MD5校验
		PreferenceUtils.putString(NFC.LostProtectedNotifiations.LostProtectedPwdKey, MD5.md5(pwd));
		return true;
	}

	@Override
	public boolean checkPwd(String pwd) {
		if (StringUtils.isEmptyOrNull(pwd)){
			return false;
		}
		String storePassword = PreferenceUtils.getString(NFC.LostProtectedNotifiations.LostProtectedPwdKey, "");
		return storePassword.equals(MD5.md5(pwd));
	}
	
	@Override
	public void checkSetupGuide() {
		boolean isSetupGuide = PreferenceUtils.getBoolean(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuideKey, false);
		if (isSetupGuide) {	//已设置
			NotificationCenter.post(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuideNotification, null);
		} else {	//未设置
			NotificationCenter.post(NFC.LostProtectedNotifiations.LostProtectedIsNotSetupGuideNotification, null);
		}
	}
	
	@Override
	public void finishSetupGuide() {
		PreferenceUtils.putBoolean(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuideKey, true);
	}
	
	@Override
	public boolean bindSim(String simSerialNumber) {
		//进行绑定Sim
		PreferenceUtils.putString("simNumber", simSerialNumber);
		return true;
	}
	
	@Override
	public boolean checkSimBind() {
		String simNumber = PreferenceUtils.getString(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuideSimNumberKey, "");
		return !StringUtils.isEmptyOrNull(simNumber);
	}
	
	@Override
	public boolean unBindSim() {
		PreferenceUtils.putString(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuideSimNumberKey, "");
		return true;
	}
	
	@Override
	public boolean bindPhone(String phone) {
		PreferenceUtils.putString(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuidePhoneKey, phone);
		return true;
	}
	
	@Override
	public boolean checkIsProtecting() {
		PreferenceUtils.getBoolean(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuideProtectingKey, false);
		return true;
	}
	
	/**
	 * 启用
	 */
	public boolean enableProtecting() {
		PreferenceUtils.putBoolean(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuideProtectingKey, true);
		return true;
	}
	
	@Override
	public String getBindPhone() {
		return PreferenceUtils.getString(NFC.LostProtectedNotifiations.LostProtectedIsSetupGuidePhoneKey, "");
	}
	
	@Override
	public String getBindSimNumber() {
		return PreferenceUtils.getString("simNumber", "");
	}
}
