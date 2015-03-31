package com.fanglong.mobilesafe.receiver;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.lost.ILostProtectedMgr;
import com.fanglong.mobilesafe.business.lost.location.GPSLocationProvider;
import com.fanglong.mobilesafe.business.lost.location.IGPSLocationMgr;
import com.fanglong.mobilesafe.common.StringUtils;
import com.fanglong.mobilesafe.common.log.Logger;
import com.fanglong.mobilesafe.common.sms.SMSUtils;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

/**
 * 短信接受者 - 监听收短信功能
 * @author apple
 *
 */
public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object [] pdus = (Object[])intent.getExtras().get("pdus");
		for (Object pdu : pdus) {
			SmsMessage message = SmsMessage.createFromPdu((byte[])pdu);
			String content = message.getMessageBody();
			Logger.debug("SMSReceiver content "+content);
			//获取绑定防盗手机号
			ILostProtectedMgr lostProtectedMgr = ServiceMgr.getService(ILostProtectedMgr.class);
			if (!lostProtectedMgr.checkIsProtecting()){
				return;
			}
			if ("#*location*#".equals(content)){		//获取位置
				Logger.debug("SMSReceiver 获取位置");
				String locationInfo = ServiceMgr.getService(IGPSLocationMgr.class).getLocation(GPSLocationProvider.getLocationChangeListener());
				if (StringUtils.isEmptyOrNull(locationInfo)){
					return;
				}
				String descAddress = lostProtectedMgr.getBindPhone();
				if (!StringUtils.isEmptyOrNull(descAddress)){
					//发送短信
					SMSUtils.sendSms(descAddress, locationInfo);
				}
			}
			if ("#*lockscreen*#".equals(content)){			//锁屏
				Logger.debug("SMSReceiver 锁屏");
				DevicePolicyManager manager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
				manager.resetPassword("123", 0);
				manager.lockNow();
			} else if ("#*reset*#".equals(content)){		//重置出厂设置
				Logger.debug("SMSReceiver 恢复出厂设置");
				DevicePolicyManager manager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
				manager.wipeData(0);
			} else if ("#*music*#".equals(content)){		//报警音乐
				Logger.debug("SMSReceiver 播放报警音乐");
				MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
				player.setVolume(1.0f, 1.0f);
				player.start();
			}
			//终止广播
			abortBroadcast();
		}
		
	}

}
