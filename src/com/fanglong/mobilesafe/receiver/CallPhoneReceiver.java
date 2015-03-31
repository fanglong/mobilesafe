package com.fanglong.mobilesafe.receiver;

import com.fanglong.mobilesafe.LoadConfig;
import com.fanglong.mobilesafe.activity.lost.LostProtectedActivity;
import com.fanglong.mobilesafe.common.log.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 手机拨打通知接受者 - 用户开启手机防盗
 * @author apple
 *
 */
public class CallPhoneReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//获取拨号号码
		String number = getResultData();
		String lostNumber = LoadConfig.getInstance().loadDataFromConfig(LoadConfig.KEY_LOST_NUMBER);
		Logger.debug(String.format("CallPhoneReceiver number %s , lostNumber : %s",number,lostNumber));
		if (lostNumber.equals(number)){
			//手机号相同时处理
			Intent lostIntent = new Intent(context,LostProtectedActivity.class);
			lostIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(lostIntent);
			setResultData(null);
		}
	}

}
