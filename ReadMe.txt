一：DeviceAdmin 创建过程介绍
1.创建DeviceAdminReceiver 子类 (MyAdmin)
1.1 注册Receiver
<receiver android:name=".MyAdmin">
	<meta-data android:name="android.app.device_admin"
			   android:resource="@xml/my_admin"/>
	<intent-filter>
		<action android:name="android.app.action.DEVICE_ADMIN_ENABLED"/>
	</intent-filter>
<receiver>

my_admin.xml
<?xml version="1.0" encoding="UTF_*" ?>
<device-admin xmlns:andriod="http://schems.android.com/apk/res/android">
	<uses-policies>
		<limit-password />	<!--限制密码 -->
		<watch-login />	 <!-- -->
		<reset-password />	<!--重置密码 -->
		<force-lock />	<!--锁屏 -->
		<wipe-data /> <!-- 出厂设置-->
	</uses-policies>
</device-admin>


2.获取IDevicePolicyManager
Method method = Class.forName("android.os.ServiceManager").getMethod("getService",Service.class);
IBinder binder = (IBinder) method.invoke(null,new Object[]{Context.DEVICE_POLICY_SERVICE});
mService = IDevicePolicyManager.Stub.asInterface(binder);


3.注册广播接受者为Admin设备
ComoonentName mAdminName = new ComoonentName(this.MyAdmin.class);


