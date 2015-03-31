package com.fanglong.mobilesafe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fanglong.mobilesafe.application.MobileSafeApplication;
import com.fanglong.mobilesafe.common.StringUtils;
import com.fanglong.mobilesafe.common.log.Logger;

public class LoadConfig {
	/**服务器地址 */
	public final static String KEY_SERVER_URL = "serverurl";
	/**日志输出级别 */
	public final static String KEY_LOG_LEVEL = "loglevel";
	
	public final static String KEY_LOST_NUMBER = "lostnumber";
	
	private Properties properties = null;
	
	private LoadConfig(){
		properties = getProperties();
	}
	
	private static class Holder {
		private final static LoadConfig config = new LoadConfig();
	}
	
	public static LoadConfig getInstance(){
		//内部类进行延迟加载
		return Holder.config; 
	}
	
	/**
	 * 从配置文件中获取配置
	 * @param key
	 * @return 获取不到返回""
	 */
	public String loadDataFromConfig(String key){
		if (properties == null) {
			properties = getProperties();
		}
		
		if (properties == null){
			return null;
		}
		return properties.getProperty(key,"");
	}
	
	private synchronized static Properties getProperties() {
		Properties prop = new Properties();
		InputStream in = MobileSafeApplication.getGlobalContext().getResources().openRawResource(R.raw.config);
		try {
			prop.load(in);
		} catch (IOException e) {
			Logger.error("load properties error" + e.getMessage());
			return null;
		}
		return prop;
	}

	/**
	 * 从配置文件中获取配置
	 * 
	 * @param key
	 * @return
	 */
	public int loadDataFromConfigForInt(String key,int defaultvalue) {
		if (properties == null) { // 如果还没有被成功初始化，再次初始化配置文件
			properties = getProperties();
		}

		if (properties == null) { // 再次初始化失败
			return defaultvalue;
		}

		String value = properties.getProperty(key, "");
		if (StringUtils.isEmptyOrNull(value)) {
			return defaultvalue;
		}
		return Integer.parseInt(value);
	}
}
