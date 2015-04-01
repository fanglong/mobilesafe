package com.fanglong.mobilesafe.business;

public final class NFC {
	/**
	 * 版本信息通知类配置
	 * @author apple
	 */
	public class VersionCheckNotifications {
	    public final static String NewVersionNotification = "NewVersionNotification";
	    public final static String LastestNewVersionNotification = "LastestNewVersionNotification";
	    public final static String NewVersionNameKey = "NewVersionNameKey";
	    public final static String NewVersionApkNameKey = "NewVersionApkNameKey";
	    public final static String NewVersionDownloadUrlKey = "NewVersionDownloadUrlKey";
	    public final static String NewVersionDescriptionKey = "NewVersionDescriptionKey";
	    public final static String NewVersionNetErrorNotification = "NewVersionNetErrorNotification";

	    public final static String NewVersionDownloadedNotification = "NewVersionDownloadedNotification";
	    public final static String NewVersionDownloadedKey = "NewVersionDownloadedKey";
	    public final static String NewVersionLocalApkPathKey = "NewVersionLocalApkPathKey";
	}
	
	/**
	 * 手机防盗
	 * @author apple
	 *
	 */
	public class LostProtectedNotifications{
		// 关键字设置
		/**密码关键字 */
		public final static String LostProtectedPwdKey = "password";
		/**是否设置向导引导关键字 */
		public final static String LostProtectedIsSetupGuideKey = "issetupguide";
		/**绑定SIM卡号 */
		public static final String LostProtectedIsSetupGuideSimNumberKey = "simNumber";
		/**绑定手机号 */
		public static final String LostProtectedIsSetupGuidePhoneKey = "safePhone";
		/**防盗保护 */
		public static final String LostProtectedIsSetupGuideProtectingKey = "protectAble";
		
		// 通知设置
		/**通知 - 防盗密码未设置 */
		public final static String LostProtectedPwdNotSetUpNotification = "LostProtectedPwdNotSetUpNotification";
		/**通知 - 防盗密码已设置 */
		public final static String LostProtectedPwdSetupedNotification = "LostProtectedPwdSetupedNotification";
		/**通知 - 未设置防盗向导 */
		public final static String LostProtectedIsNotSetupGuideNotification = "LostProtectedNotSetupGuideNotification";
		/**通知 - 已设置防盗向导 */
		public final static String LostProtectedIsSetupGuideNotification = "LostProtectedIsSetupGuideNotification";
		
		
		 
	}
	
	/**
	 * 高级工具
	 * @author apple
	 *
	 */
	public class AtoolNotification{
		//关键字设置
		/**手机归属地查询 - 手机号 */
		public final static String AtoolAfficationQueryPhoneNumber = "phoneNumber";
		/**手机归属地查询 - 归属地 */
		public final static String AtoolAfficationQueryLocation = "location";
		
		//通知设置
		/**手机号归属地查询 */
		public final static String AtoolAfficationQueryNotification = "AtoolAfficationQueryNotification";
	}
}
