package com.fanglong.mobilesafe.receiver;

import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.lost.ILostProtectedMgr;
import com.fanglong.mobilesafe.common.StringUtils;
import com.fanglong.mobilesafe.common.log.Logger;
import com.fanglong.mobilesafe.service.MobileSafeService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * 引导广播接受者 - 用于启动自定义Service
 * @author apple
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {//安装引导
			startMobileSafeService(context);
		} else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) { //包替换
			String selfPackageName = context.getPackageName();
			Logger.info(intent.getComponent().getPackageName() + "was replaced by new package , self package" + selfPackageName);
			if (StringUtils.isEqualCaseInsensitive(selfPackageName,intent.getComponent().getPackageName())){
				startMobileSafeService(context);
			}
		}
		ILostProtectedMgr iLostProtectedMgr = ServiceMgr.getService(ILostProtectedMgr.class);
		if (iLostProtectedMgr.checkIsProtecting()){
			//检查sim卡串号
			TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			String simSerialNumber = telephonyManager.getSimSerialNumber();
			if ("".equals(simSerialNumber)){
				return;
			}
			String bindSim = iLostProtectedMgr.getBindSimNumber(); 
			if ("".equals(bindSim)){
				return;
			}
			if (!simSerialNumber.equals(bindSim)){
				//发送短信
				SmsManager manager = SmsManager.getDefault();
				String descAdress = iLostProtectedMgr.getBindPhone();	//目标手机号
				if (StringUtils.isEmptyOrNull(descAdress)){
					Logger.error("无防盗绑定手机号");
					return;
				}
				manager.sendTextMessage(descAdress, null, "您的SIM卡发送改变，可能被盗", null,null);
			}
		}
	}
	
	private void startMobileSafeService (Context context) {
		Intent intent = new Intent(context,MobileSafeService.class);
		context.startService(intent);
	}
}
