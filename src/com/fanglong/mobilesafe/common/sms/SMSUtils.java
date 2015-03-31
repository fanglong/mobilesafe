package com.fanglong.mobilesafe.common.sms;

import android.app.PendingIntent;
import android.telephony.SmsManager;

/**
 * 消息帮助类
 * @author apple
 */
public class SMSUtils {
	/**
	 * 发送短信
	 * @param descAddress		目标地址
	 * @param scAddress			源地址
	 * @param text				内容
	 * @param sentIntent		发送意图
	 * @param deliveryIntent	递交意图
	 */
	public static void sendSms(String descAddress,String scAddress,String text
								,PendingIntent sentIntent,PendingIntent deliveryIntent){
		SmsManager manager = SmsManager.getDefault();
		manager.sendTextMessage(descAddress, scAddress, text, sentIntent,deliveryIntent);
	}
	
	/**
	 * 发送短信
	 * @param descAddress	目标地址
	 * @param text			内容
	 */
	public static void sendSms(String descAddress,String text){
		sendSms(descAddress, null, text, null, null);
	}
}
