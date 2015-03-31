package com.fanglong.mobilesafe.activity.lost.setup;

/**
 * 
 * @author apple
 *
 */
public interface OnSwitchChangeListener {
	
	/**
	 * 显示下一个
	 * @param currentIndex
	 * @return
	 */
	boolean showNext(int currentIndex);
	
	/**
	 * 显示上一个
	 * @param currentIndex
	 * @return
	 */
	boolean showPrevious(int currentIndex);
}
