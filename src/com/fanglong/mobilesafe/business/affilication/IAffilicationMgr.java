package com.fanglong.mobilesafe.business.affilication;

public interface IAffilicationMgr {
	
	/**
	 * 通过手机号查询归属地
	 * @param number
	 */
	void queryAllicationByPhoneNumber(String number);

}
