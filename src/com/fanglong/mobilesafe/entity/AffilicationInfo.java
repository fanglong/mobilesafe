package com.fanglong.mobilesafe.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 归属地信息
 * @author apple
 *
 */
@Table(name="address_tb")
public class AffilicationInfo {
	@Id(column="_id")
	private int id;							//主键id
	@Column(column="city")	
	private String city;					//城市
	@Column(column="cardtype")
	private String cardtype;				//卡类型
	@Column(column="area")
	private String area;					//区号
	
	public AffilicationInfo() {
	
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
	
}
