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
		String password = PreferenceUtils.getString(NFC.LostProtectedNotifications.LostProtectedPwdKey, "");
		//验证密码是否设置
		if (StringUtils.isEmptyOrNull(password)){
			NotificationCenter.post(NFC.LostProtectedNotifications.LostProtectedPwdNotSetUpNotification, null);
		} else {
			NotificationCenter.post(NFC.LostProtectedNotifications.LostProtectedPwdSetupedNotification, null);
		}
	}

	@Override
	public boolean updatePwd(String pwd) {
		//进行MD5校验
		PreferenceUtils.putString(NFC.LostProtectedNotifications.LostProtectedPwdKey, MD5.md5(pwd));
		return true;
	}

	@Override
	public boolean checkPwd(String pwd) {
		if (StringUtils.isEmptyOrNull(pwd)){
			return false;
		}
		String storePassword = PreferenceUtils.getString(NFC.LostProtectedNotifications.LostProtectedPwdKey, "");
		return storePassword.equals(MD5.md5(pwd));
	}
	
	@Override
	public void checkSetupGuide() {
		boolean isSetupGuide = PreferenceUtils.getBoolean(NFC.LostProtectedNotifications.LostProtectedIsSetupGuideKey, false);
		if (isSetupGuide) {	//已设置
			NotificationCenter.post(NFC.LostProtectedNotifications.LostProtectedIsSetupGuideNotification, null);
		} else {	//未设置
			NotificationCenter.post(NFC.LostProtectedNotifications.LostProtectedIsNotSetupGuideNotification, null);
		}
	}
	
	@Override
	public void finishSetupGuide() {
		PreferenceUtils.putBoolean(NFC.LostProtectedNotifications.LostProtectedIsSetupGuideKey, true);
	}
	
	@Override
	public boolean bindSim(String simSerialNumber) {
		//进行绑定Sim
		PreferenceUtils.putString("simNumber", simSerialNumber);
		return true;
	}
	
	@Override
	public boolean checkSimBind() {
		String simNumber = PreferenceUtils.getString(NFC.LostProtectedNotifications.LostProtectedIsSetupGuideSimNumberKey, "");
		return !StringUtils.isEmptyOrNull(simNumber);
	}
	
	@Override
	public boolean unBindSim() {
		PreferenceUtils.putString(NFC.LostProtectedNotifications.LostProtectedIsSetupGuideSimNumberKey, "");
		return true;
	}
	
	@Override
	public boolean bindPhone(String phone) {
		PreferenceUtils.putString(NFC.LostProtectedNotifications.LostProtectedIsSetupGuidePhoneKey, phone);
		return true;
	}
	
	@Override
	public boolean checkIsProtecting() {
		PreferenceUtils.getBoolean(NFC.LostProtectedNotifications.LostProtectedIsSetupGuideProtectingKey, false);
		return true;
	}
	
	/**
	 * 启用
	 */
	public boolean enableProtecting() {
		PreferenceUtils.putBoolean(NFC.LostProtectedNotifications.LostProtectedIsSetupGuideProtectingKey, true);
		return true;
	}
	
	@Override
	public String getBindPhone() {
		return PreferenceUtils.getString(NFC.LostProtectedNotifications.LostProtectedIsSetupGuidePhoneKey, "");
	}
	
	@Override
	public String getBindSimNumber() {
		return PreferenceUtils.getString("simNumber", "");
	}
}
