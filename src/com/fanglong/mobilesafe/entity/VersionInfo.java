package com.fanglong.mobilesafe.entity;

public class VersionInfo {
	private String version;
	
	private String apkUrl;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	
	public boolean compareVersion(String version){
		if (this.version.compareTo(version) > 0){
			return true;
		}
		return false;
	}
}
