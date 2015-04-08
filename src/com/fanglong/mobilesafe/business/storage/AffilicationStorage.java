package com.fanglong.mobilesafe.business.storage;

import com.lidroid.xutils.DbUtils;

public class AffilicationStorage extends IAffilicationStorage {
	private DbUtils privateStorage;
	
	public AffilicationStorage(DbUtils privateStorage) {
		this.privateStorage = privateStorage;
	}
}
