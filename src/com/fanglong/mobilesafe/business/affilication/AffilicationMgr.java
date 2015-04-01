package com.fanglong.mobilesafe.business.affilication;

import android.content.Intent;

import com.fanglong.mobilesafe.business.BaseMgr;
import com.fanglong.mobilesafe.business.NFC;
import com.fanglong.mobilesafe.business.ServiceFactory;
import com.fanglong.mobilesafe.common.notification.NotificationCenter;
import com.lidroid.xutils.DbUtils;

public class AffilicationMgr extends BaseMgr implements IAffilicationMgr{

	public AffilicationMgr(ServiceFactory factory) {
		super(factory);
	}
	
	@Override
	public void queryAllicationByPhoneNumber(String number) {
		//数据库查询
		//设置响应
		Intent intent = new Intent();
		intent.putExtra(NFC.AtoolNotification.AtoolAfficationQueryPhoneNumber, number);
		String location = "北京";
		intent.putExtra(NFC.AtoolNotification.AtoolAfficationQueryLocation, location);
		NotificationCenter.post(NFC.AtoolNotification.AtoolAfficationQueryNotification, intent);
	}
}
