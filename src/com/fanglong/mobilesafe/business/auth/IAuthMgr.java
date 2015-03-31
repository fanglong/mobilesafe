package com.fanglong.mobilesafe.business.auth;

public interface IAuthMgr {
	public static interface AuthFailedListener {
        public void onAuthFailed();
    }

	void setAuthFailedListener(AuthFailedListener listener);;
}
