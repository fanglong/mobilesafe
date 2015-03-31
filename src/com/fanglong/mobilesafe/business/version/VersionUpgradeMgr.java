package com.fanglong.mobilesafe.business.version;

import android.content.Intent;

import com.fanglong.mobilesafe.business.BaseMgr;
import com.fanglong.mobilesafe.business.NFC;
import com.fanglong.mobilesafe.business.ServiceFactory;
import com.fanglong.mobilesafe.common.notification.NotificationCenter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class VersionUpgradeMgr extends BaseMgr implements IVersionUpgradeMgr {

	public VersionUpgradeMgr(ServiceFactory factory) {
		super(factory);
	}

	@Override
	public void checkNewVersion() {
		//发送请求
		HttpUtils httpUtils = new HttpUtils();
		String url = "";
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Intent intent = new Intent();
				intent.putExtra(NFC.VersionCheckNotifications.NewVersionApkNameKey, "");	//apkname
				NotificationCenter.post(NFC.VersionCheckNotifications.NewVersionNotification, intent);
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				NotificationCenter.post(NFC.VersionCheckNotifications.NewVersionNetErrorNotification, null);
			}
		});
	}

	@Override
	public void downloadNewVersion(String apkName, String downloadUrl) {

	}

	@Override
	public boolean getApkDowningState() {
		return false;
	}

}
