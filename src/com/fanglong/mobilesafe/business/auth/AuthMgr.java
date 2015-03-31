package com.fanglong.mobilesafe.business.auth;

import android.content.Context;

public class AuthMgr implements IAuthMgr {
	
	public AuthMgr(Context context) {
		
	}
	
	private AuthFailedListener listener = null;
	
	@Override
	public void setAuthFailedListener(AuthFailedListener listener) {
		this.listener = listener;
	}
}
