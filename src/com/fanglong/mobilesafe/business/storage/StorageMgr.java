package com.fanglong.mobilesafe.business.storage;

import com.fanglong.mobilesafe.application.MobileSafeApplication;
import com.fanglong.mobilesafe.business.BaseMgr;
import com.fanglong.mobilesafe.business.ServiceFactory;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;

public class StorageMgr extends BaseMgr implements IStorageMgr {
	
	private final static String kDatabasePrefix = "mobilesafe";			//数据库前缀
	private final static String kDatabaseSuffix = ".db";				//数据库后缀
	private final static int kDatabaseVersion = 10;						//数据库版本号
	
	private IAffilicationStorage affilicationStorage;					//归属地
	
	private DbUtils dbUtils;											//业务数据库
	private DbUtils affilicationDB;										//归属地数据源
	
	private DbUpgradeListener privateDbUpgradeListener = new DbUpgradeListener() {
		
		@Override
		public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
			//版本进行处理
			switch (oldVersion) {
			case 1:
				//初始化数据库
				
			default:
				break;
			}
		}
	}; 
	
	public StorageMgr(ServiceFactory factory) {
		super(factory);
	}
	
	public synchronized DbUtils getPrivateStorage(){
		if (dbUtils == null){
			String dbName = kDatabasePrefix + "_v" +kDatabaseVersion+kDatabaseSuffix;
			dbUtils = DbUtils.create(MobileSafeApplication.getGlobalContext(), dbName, kDatabaseVersion, privateDbUpgradeListener);
		}
		return dbUtils;
	}
	
	public synchronized DbUtils getAffStorage(){
		if (affilicationDB == null){
			String dbName = kDatabasePrefix+"_"+kDatabaseSuffix;
			affilicationDB = DbUtils.create(MobileSafeApplication.getGlobalContext(), dbName, kDatabaseVersion, privateDbUpgradeListener);
		}
		return affilicationDB;
	}
	
	/**
	 * 获取归属地查询
	 * @return
	 */
	public synchronized IAffilicationStorage getAffilicationStorage(){
		if (this.affilicationStorage == null){
			this.affilicationStorage = new AffilicationStorage(getAffStorage());
		}
		return this.affilicationStorage;
	}
}
