package com.fanglong.mobilesafe.activity.tool.affiliation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseActivity;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.affilication.IAffilicationMgr;
import com.fanglong.mobilesafe.common.StringUtils;
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
		
		ViewUtils.inject(this);
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
}
