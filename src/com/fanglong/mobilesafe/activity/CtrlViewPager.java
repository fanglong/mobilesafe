package com.fanglong.mobilesafe.activity;

public interface CtrlViewPager {
	
	/**
	 * 是否能够显示下一页
	 * @return
	 */
	public boolean canNext();
	
	/**
	 * 显示下一页
	 * @return
	 */
	public boolean showNext();
}
