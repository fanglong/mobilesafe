package com.fanglong.mobilesafe.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseActivity;
import com.fanglong.mobilesafe.activity.lost.LostProtectedActivity;
import com.fanglong.mobilesafe.activity.tool.AtoolsActivity;
import com.fanglong.mobilesafe.common.log.Logger;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 手机主功能页面
 * @author apple
 *
 */
public class MainActivity extends BaseActivity implements OnItemClickListener{
	
	@ViewInject(R.id.gv_main)
	private GridView gvGrid;
	
	private MainAdapter mainAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_content);
		//xUtils 设置
		ViewUtils.inject(this);
		
		mainAdapter = new MainAdapter(this);
		gvGrid.setAdapter(mainAdapter);
		gvGrid.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
		Logger.debug("position : "+postion);
		switch (postion) {
		case 0:
			selectLostProtected(view);
			break;
		case 7:
			selectAtools(view);
		default:
			break;
		}
	}
	
	/**
	 * 选择高级工具
	 * @param view
	 */
	private void selectAtools(View view) {
		Intent atoolsIntent = new Intent(this,AtoolsActivity.class);
		startActivity(atoolsIntent);
	}

	/**
	 * 选择防盗
	 * @param view
	 */
	private void selectLostProtected(View view) {
		Intent itent = new Intent(this, LostProtectedActivity.class);
		startActivity(itent);
	}
	
	
}
