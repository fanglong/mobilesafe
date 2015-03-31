package com.fanglong.mobilesafe.business.lost;

public interface ILostProtectedMgr {
	
	/**
	 * 检查防盗密码是否设置
	 */
	void checkPasswordSetup();

	/**
	 * 修改密码
	 * @param pwd
	 * @return
	 */
	boolean updatePwd(String pwd);

	/**
	 * 验证密码
	 * @param pwd
	 * @return
	 */
	boolean checkPwd(String pwd);

	/**
	 * 检查是否设置 引用向导
	 */
	void checkSetupGuide();
	
	/**
	 * 绑定SIM卡
	 * @param simSerialNumber sim序列化号
	 */
	boolean bindSim(String simSerialNumber);
	
	/**
	 * 检查SIM卡是否绑定
	 * @return 绑定返回true 反之返回false
	 */
	boolean checkSimBind();

	/**
	 * 解除绑定SIM卡
	 * @return
	 */
	boolean unBindSim();

	/**
	 * 绑定手机号
	 * @param phone
	 * @return 绑定成功 返回true 反之返回false
	 */
	boolean bindPhone(String phone);
	
	/**
	 * 验证是防盗否在保护中
	 * @return 处于保护状态：true 反之 false
	 */
	boolean checkIsProtecting();
	
	/**
	 * 开启防盗
	 * @return
	 */
	boolean enableProtecting();

	/**
	 * 完成设置向导
	 */
	void finishSetupGuide();
	
	/**
	 * 获取绑定手机号
	 * @return 有则返回， 无则返回""
	 */
	String getBindPhone();

	/**
	 * 获取绑定SIM卡
	 * @return
	 */
	String getBindSimNumber();

}
