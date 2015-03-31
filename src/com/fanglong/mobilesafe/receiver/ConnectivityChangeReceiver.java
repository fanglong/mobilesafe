package com.fanglong.mobilesafe.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fanglong.mobilesafe.common.NetworkStatus;
import com.fanglong.mobilesafe.common.log.Logger;

/**
 * 网络连接状态改变接受者
 * @author apple
 */
public class ConnectivityChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Logger.info("received connectivity changed event");
        NetworkStatus.getInstance().detect(context);
	}

}
