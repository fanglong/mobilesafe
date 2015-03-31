package com.fanglong.mobilesafe.business.version;

public interface IVersionUpgradeMgr {
	/**
	 * 检查新版本
	 */
 	public void checkNewVersion();
 	
 	/**
 	 * 下载新版本
 	 * @param apkName		apk名称
 	 * @param downloadUrl	下载地址
 	 */
    public void downloadNewVersion(String apkName, String downloadUrl);
    
    /**
     * 获取apk下载状态
     * @return
     */
    public boolean getApkDowningState();
}
