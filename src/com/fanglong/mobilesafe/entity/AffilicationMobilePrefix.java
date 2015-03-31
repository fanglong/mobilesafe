package com.fanglong.mobilesafe.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name="numinfo")
public class AffilicationMobilePrefix {
	@Column(column="mobileprefix")
	private String prefix;
	@Column(column="outkey")
	private int affid;
	
	public AffilicationMobilePrefix() {
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getAffid() {
		return affid;
	}

	public void setAffid(int affid) {
		this.affid = affid;
	}
	
	
}
