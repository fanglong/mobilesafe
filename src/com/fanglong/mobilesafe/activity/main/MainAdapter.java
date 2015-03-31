package com.fanglong.mobilesafe.activity.main;


import com.fanglong.mobilesafe.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter{
	
	private static int [] itemNames = {R.string.main_item_name_lostprotected,R.string.main_item_name_commu,R.string.main_item_name_software
		,R.string.main_item_name_task,R.string.main_item_name_flow,R.string.main_item_name_kill
		,R.string.main_item_name_os_opt,R.string.main_item_name_heightools,R.string.main_item_name_setting};
	
	private static int [] itemIcons = {R.drawable.main_item_icon_lostprotected,R.drawable.main_item_icon_commu,R.drawable.main_item_icon_software
		,R.drawable.main_item_icon_task,R.drawable.main_item_icon_flow,R.drawable.main_item_icon_kill
		,R.drawable.main_item_icon_os_opt,R.drawable.main_item_icon_heighttools,R.drawable.main_item_icon_setting};
	
	private LayoutInflater inflater;
	private ImageView itemIcon;
	private TextView itemNameView;
	
	public MainAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return itemIcons.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflater.inflate(R.layout.main_activity_item, null);
		
		itemIcon 	 = (ImageView)view.findViewById(R.id.iv_main_item_icon);
		itemNameView = (TextView)view.findViewById(R.id.tv_main_item_name);
		itemIcon.setImageResource(itemIcons[position]);
		itemNameView.setText(itemNames[position]);
		return view;
	}
	
}
