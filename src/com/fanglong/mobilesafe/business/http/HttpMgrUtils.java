package com.fanglong.mobilesafe.business.http;

import java.util.HashMap;
import java.util.Map;
import com.fanglong.mobilesafe.LoadConfig;

public class HttpMgrUtils {
	private final static Map<String, String> gServicesMap = new HashMap<String, String>();
	
	private final static String obtainAppConfig = "appConfigUrl";
	
	static {
		gServicesMap.put(obtainAppConfig, LoadConfig.getInstance().loadDataFromConfig(LoadConfig.KEY_SERVER_URL));
	}
	
	//接口地址配置
	/**服务-检查新版本 */
	public static final String SERVER_CHECK_NEW_VERSION = "checkNewVersion";
	
}
