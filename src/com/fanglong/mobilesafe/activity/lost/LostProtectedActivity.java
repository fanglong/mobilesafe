package com.fanglong.mobilesafe.activity.lost;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseActivity;
import com.fanglong.mobilesafe.activity.lost.setup.LostProtectedSetupguideActivity;
import com.fanglong.mobilesafe.business.NFC;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.lost.ILostProtectedMgr;
import com.fanglong.mobilesafe.common.StringUtils;
import com.fanglong.mobilesafe.common.log.Logger;
import com.fanglong.mobilesafe.common.notification.NotificationCenter;
import com.fanglong.mobilesafe.common.notification.NotificationHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

/**
 * 手机防盗页面
 * @author apple
 */
public class LostProtectedActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener{
	@ViewInject(R.id.tv_safemobile)
	private TextView tvSafemobile;
	
	@ViewInject(R.id.cb_lostprotect)
	private CheckBox cbLostprotect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lostprotected_activity_content);
		
		NotificationCenter.scanHandlers(this);
		ViewUtils.inject(this);
		
		checkPasswordSetup();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//清空监听处理
		NotificationCenter.clearHandlers(this);
		
	}
	
	/**
	 * 检查是否初始化密码
	 */
	private void checkPasswordSetup() {
		ServiceMgr.getService(ILostProtectedMgr.class).checkPasswordSetup();
	}
	
	/**
	 * 检查是否设置向导
	 */
	private void checkSetupguide() {
		ILostProtectedMgr iLostProtectedMgr = ServiceMgr.getService(ILostProtectedMgr.class);
		if (iLostProtectedMgr.checkIsProtecting()){
			cbLostprotect.setChecked(true);
		}
		String bindPhone = iLostProtectedMgr.getBindPhone();
		if (StringUtils.isEmptyOrNull(bindPhone)){
			tvSafemobile.setText(getString(R.string.tv_lostprotect_safemobile) + " 无绑定手机号");
		} else {
			tvSafemobile.setText(getString(R.string.tv_lostprotect_safemobile) + bindPhone);	
		}
		
	}
	
	@OnCompoundButtonCheckedChange({R.id.cb_lostprotect})
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked){
			cbLostprotect.setText(getString(R.string.cb_lostportect_protecting_tip));
		} else {
			cbLostprotect.setText(getString(R.string.cb_lostportect_not_protecting_tip));
		}
	}
	
	private Dialog dialog;
	private TextView etPwd;
	private TextView etPwdAgent;
	
	/**
	 * 处理密码未设置
	 * @param intent
	 */
	@NotificationHandler(name=NFC.LostProtectedNotifications.LostProtectedPwdNotSetUpNotification)
	public void handlerCheckPasswordNotSetup(Intent intent){
		Logger.debug("LostProtectedActivity  用户未设置防盗密码");
		dialog = new Dialog(LostProtectedActivity.this,R.style.MyDialog);
		View view = View.inflate(LostProtectedActivity.this, R.layout.lostprotected_activity_first_pwd_dialog, null);
		
		view.findViewById(R.id.bt_first_dialog_ok).setOnClickListener(this);
		view.findViewById(R.id.bt_first_dialog_cancle).setOnClickListener(this);

		etPwd = (TextView)view.findViewById(R.id.et_dialog_lostprotected_setup_pwd);
		etPwdAgent = (TextView)view.findViewById(R.id.et_dialog_lostprotected_setup_pwd_agent);
		
		dialog.setContentView(view);
		dialog.show();
		
	}
	
	/**
	 * 处理密码已设置
	 * @param intent
	 */
	@NotificationHandler(name=NFC.LostProtectedNotifications.LostProtectedPwdSetupedNotification)
	public void handlerCheckPasswordSetuped(Intent intent){
		Logger.debug("LostProtectedActivity  用户已设置防盗密码");
		dialog = new Dialog(this, R.style.MyDialog);
		View view = View.inflate(LostProtectedActivity.this, R.layout.lostprotected_activity_normal_pwd_dialog, null);
		
		view.findViewById(R.id.bt_normal_dialog_cancle).setOnClickListener(this);
		view.findViewById(R.id.bt_normal_dialog_ok).setOnClickListener(this);
		etPwd = (TextView)view.findViewById(R.id.et_dialog_lostprotected_setup_pwd);
		
		dialog.setContentView(view);
		dialog.show();
	}
	
	@OnClick({R.id.cb_lostprotect
			 ,R.id.tv_reset_lostprotect})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_first_dialog_ok:
			//数据校验
			String pwd = etPwd.getText().toString();
			String pwdAgent = etPwdAgent.getText().toString();
			if (StringUtils.isEmptyOrNull(pwd)){
				showToastMessage(R.string.tip_pwd_is_empty);
				return;
			}
			if (!pwd.equals(pwdAgent)){
				showToastMessage(R.string.tip_pwd_agent_not_equals);
				return;
			}
			if (ServiceMgr.getService(ILostProtectedMgr.class).updatePwd(pwd)){
				dialog.dismiss();
			}
			break;
		case R.id.bt_normal_dialog_ok:
			pwd = etPwd.getText().toString();
			if (StringUtils.isEmptyOrNull(pwd)){
				showToastMessage(R.string.tip_pwd_is_empty);;
				return;
			}
			if (!ServiceMgr.getService(ILostProtectedMgr.class).checkPwd(pwd)){
				showToastMessage(R.string.tip_pwd_is_error);
				return;
			}
			dialog.dismiss();
			//检查是否已绑定数据
			checkSetupguide();
			break;
		case R.id.bt_first_dialog_cancle:
		case R.id.bt_normal_dialog_cancle:
			dialog.dismiss();
			finish();
			return;
		case R.id.tv_reset_lostprotect:
			handlerIsNotSetupGuideNotification(null);
			return;
		default:
			break;
		}
		//验证密码通过  判断是否设置防盗向导
		ServiceMgr.getService(ILostProtectedMgr.class).checkSetupGuide();
	}
	
	/**
	 * 已设置 - 向导
	 * @param handler
	 */
	@NotificationHandler(name=NFC.LostProtectedNotifications.LostProtectedIsSetupGuideNotification)
	public void handlerIsSetupGuideNotification(Intent handler){
		Logger.debug("LostProtectedActivity   已设置向导 ");
	}
	
	/**
	 * 未设置 - 向导
	 * @param handler
	 */
	@NotificationHandler(name=NFC.LostProtectedNotifications.LostProtectedIsNotSetupGuideNotification)
	public void handlerIsNotSetupGuideNotification(Intent handler){
		Logger.debug("LostProtectedActivity   未设置向导 ");
		Intent guideIntent = new Intent(LostProtectedActivity.this,LostProtectedSetupguideActivity.class);
		startActivity(guideIntent);
		
		finish();
	}
}
