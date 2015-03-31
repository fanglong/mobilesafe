package com.fanglong.mobilesafe.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseActivity;
import com.fanglong.mobilesafe.activity.main.MainActivity;
import com.fanglong.mobilesafe.business.NFC;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.version.IVersionUpgradeMgr;
import com.fanglong.mobilesafe.business.version.UninstallClean;
import com.fanglong.mobilesafe.common.PackageUtils;
import com.fanglong.mobilesafe.common.notification.NotificationCenter;
import com.fanglong.mobilesafe.common.notification.NotificationHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 初始化启动页面
 * @author apple
 *
 */
public class SplashActivity extends BaseActivity {
	@ViewInject(R.id.ll_splash_main)
	private LinearLayout llSplashMain;
	
	@ViewInject(R.id.tv_splash_version)
	private TextView tvSplashVersion;
	
	private Handler h = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NotificationCenter.scanHandlers(this);
		
		// 设置窗体无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_activity_content);
		
		//xUtils 设置
		ViewUtils.inject(this);
		
		// 获取当前版本号并设置
		String version = PackageUtils.getVersionName(this);
		tvSplashVersion = (TextView)findViewById(R.id.tv_splash_version);
		tvSplashVersion.setText(version);
		
		//检查新版本
		checkNewVersion();
		
		// 设置加载动画效果
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(2000);
		llSplashMain.startAnimation(alphaAnimation);
		
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.MEMORY_TYPE_NORMAL);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//清空监听
		NotificationCenter.clearHandlers(this);
	}
	
	/**
	 * 检查新版本
	 */
	private void checkNewVersion() {
		ServiceMgr.getService(IVersionUpgradeMgr.class).checkNewVersion();
	}

	/**
	 * 版本信息更新通知
	 * @param handler
	 */
	@NotificationHandler(name=NFC.VersionCheckNotifications.NewVersionNotification)
	private void handlerCheckNewVersion(Intent handler){
		Log.e("NewVersion", "新版本更新");
		//验证是否第一次安装
		if (!UninstallClean.getInstallState()){ //第一次安装  加载使用动画
			 h.postDelayed(new SpalshTask(), 4000);
		} else { 	
			 h.postDelayed(new SpalshTask(), 2000);
		}
	}
	
	/**
	 * 版本更新网络异常通知
	 * @param handler
	 */
	@NotificationHandler(name=NFC.VersionCheckNotifications.NewVersionNetErrorNotification)
	private void handlerNetworkErrorVersion(Intent handler){
		Log.e("NewVersion", "版本检查网络错误");
		//验证是否第一次安装
		if (!UninstallClean.getInstallState()){ //第一次安装  加载使用动画
			 h.postDelayed(new SpalshTask(), 4000);
		} else { 	
			 h.postDelayed(new SpalshTask(), 2000);
		}
	}
	
	class SpalshTask implements Runnable{
		@Override
		public void run() {
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			// 用户演示使用动画
//			if (PreferenceUtils.getBoolean("first_enter_app", true)) {
//				PreferenceUtils.putBoolean("first_enter_app", false);
//				intent = new Intent(SplashActivity.this, ManualViewPagerActivity.class);
//				intent.putExtra("item", 4);
//				startActivity(intent);
//			}
			SplashActivity.this.finish();
		}
	}
}
