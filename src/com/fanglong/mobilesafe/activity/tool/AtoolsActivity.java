package com.fanglong.mobilesafe.activity.tool;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 高级工具
 * @author apple
 */
public class AtoolsActivity extends BaseActivity {
	@ViewInject(R.id.tv_phone_address)
	private TextView tvPhoneAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.atools_activity_content);
		ViewUtils.inject(this);
	}
	
	@OnClick({R.id.tv_phone_address})
	public void onClick(View view){
		//归属地查询  吗。筽                  。
	}
}
