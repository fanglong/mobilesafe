package com.fanglong.mobilesafe.activity.tool.affiliation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseActivity;
import com.fanglong.mobilesafe.business.NFC;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.affilication.IAffilicationMgr;
import com.fanglong.mobilesafe.common.StringUtils;
import com.fanglong.mobilesafe.common.notification.NotificationCenter;
import com.fanglong.mobilesafe.common.notification.NotificationHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 归属地查询
 * @author apple
 */
public class AffiliationActivity extends BaseActivity {
	@ViewInject(R.id.et_query_number)
	private EditText etQueryNumber;
	@ViewInject(R.id.bt_query)
	private Button btQuery;
	@ViewInject(R.id.tv_query_number_address)
	private TextView tvQueryNumberAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//进行处理
		setContentView(R.layout.atools_affilication_activity_content);
		NotificationCenter.scanHandlers(this);
		ViewUtils.inject(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		NotificationCenter.clearHandlers(this);
	}
	
	@OnClick(value={R.id.bt_query})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.bt_query:
			//归属地查询
			findAffication();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 归属地查询
	 */
	private void findAffication() {
		//获取输入手机号
		String number = etQueryNumber.getText().toString().trim();
		//判断手机号合法性
		if (StringUtils.isEmptyOrNull(number)){
			//进行提示
			showToastMessage("输入手机号非法，请重新输入");
			return;
		}
		//查询所属地
		ServiceMgr.getService(IAffilicationMgr.class).queryAllicationByPhoneNumber(number);
	}
	
	/**
	 * 高级工具 - 手机号归属地查询
	 * @param intent {@link NFC.AtoolNotification} 
	 * 	AtoolAfficationQueryPhoneNumber,
	 *  AtoolAfficationQueryLocation
	 */
	@NotificationHandler(name=NFC.AtoolNotification.AtoolAfficationQueryNotification)
	public void handlerAtoolAfficationQueryNotification(Intent intent){
		if (intent == null){
			showToastMessage("归属地查询失败");
			return;
		}
		//手机号
		String phoneNumber = intent.getStringExtra(NFC.AtoolNotification.AtoolAfficationQueryPhoneNumber);
		//归属地
		String location = intent.getStringExtra(NFC.AtoolNotification.AtoolAfficationQueryLocation);
		//设置归属地
		tvQueryNumberAddress.setText(String.format("手机号%s 归属地为：%s", phoneNumber,location));
	}
}
