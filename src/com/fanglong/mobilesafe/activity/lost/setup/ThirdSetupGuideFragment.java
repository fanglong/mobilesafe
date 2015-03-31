package com.fanglong.mobilesafe.activity.lost.setup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fanglong.mobilesafe.R;
import com.fanglong.mobilesafe.activity.BaseFragment;
import com.fanglong.mobilesafe.business.ServiceMgr;
import com.fanglong.mobilesafe.business.lost.ILostProtectedMgr;
import com.fanglong.mobilesafe.common.StringUtils;
import com.fanglong.mobilesafe.common.log.Logger;

/**
 * 第三步 - 安全手机号绑定
 * @author apple
 */
public class ThirdSetupGuideFragment extends BaseFragment implements View.OnClickListener{
	private int currentIndex;
	private OnSwitchChangeListener onSwitchChangeListener;
	
	private EditText etLostprotectedNumber;
	private Button btSelectContact;
	
	public ThirdSetupGuideFragment(int currentIndex) {
		super();
		this.currentIndex = currentIndex;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lostprotected_setupguide_fragment_third_content, null);
		view.findViewById(R.id.bt_next).setOnClickListener(this);
		view.findViewById(R.id.bt_previous).setOnClickListener(this);
		etLostprotectedNumber = (EditText)view.findViewById(R.id.et_lostprotected_number);
		btSelectContact = (Button)view.findViewById(R.id.bt_select_contact);
		btSelectContact.setOnClickListener(this);
		return view;
	}
	
	public void setOnNextChangeListener(
			OnSwitchChangeListener onSwitchChangeListener) {
		this.onSwitchChangeListener = onSwitchChangeListener;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_select_contact:	//选择联系人
			Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, currentIndex);
			break;
		case R.id.bt_next:	//下一步
			//绑定安全手机号
			if(!bindPhonenumber()){
				showToastMessage(R.string.msg_toast_input_phone_invalid);
				return;
			}
			if (onSwitchChangeListener != null) 
				onSwitchChangeListener.showNext(currentIndex);
			break;
		case R.id.bt_previous: //上一步
			if (onSwitchChangeListener != null)
				onSwitchChangeListener.showPrevious(currentIndex);
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logger.debug("onActivityResult 选择联系人");
		if (requestCode == currentIndex){
			if (resultCode == Activity.RESULT_OK){
				//获取手机号
				Uri contactData = data.getData();
				Cursor cursor = getActivity().managedQuery(contactData, null, null, null,null);  
                cursor.moveToFirst();  
                String phone = getContactPhone(cursor);
                etLostprotectedNumber.setText(phone);
			}
		}
	}
	
	public String getContactPhone(Cursor cursor){
		int phoneColumn = cursor
				.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
		int phoneNum = cursor.getInt(phoneColumn);
		String result = "";
		if (phoneNum > 0) {
			// 获得联系人的ID号
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			String contactId = cursor.getString(idColumn);
			// 获得联系人电话的cursor
			Cursor phone = getActivity().getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ contactId, null, null);
			if (phone.moveToFirst()) {
				for (; !phone.isAfterLast(); phone.moveToNext()) {
					int index = phone
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
					String phoneNumber = phone.getString(index);
					result = phoneNumber;
				}
				if (!phone.isClosed()) {
					phone.close();
				}
			}
		}
		return result;
	}
	/**
	 * 绑定手机号
	 * @return
	 */
	private boolean bindPhonenumber() {
		//获取手机号
		String phone = etLostprotectedNumber.getText().toString().trim();
		if (StringUtils.isPhone(phone)){
			ServiceMgr.getService(ILostProtectedMgr.class).bindPhone(phone);
			return true;
		}
		return false;
	}
}
