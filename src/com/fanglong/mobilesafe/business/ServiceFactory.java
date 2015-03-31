package com.fanglong.mobilesafe.business;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.fanglong.mobilesafe.activity.tool.affiliation.AffiliationActivity;
import com.fanglong.mobilesafe.business.affilication.IAffilicationMgr;
import com.fanglong.mobilesafe.business.auth.IAuthMgr;
import com.fanglong.mobilesafe.business.lost.ILostProtectedMgr;
import com.fanglong.mobilesafe.business.lost.LostProtectedMgr;
import com.fanglong.mobilesafe.business.lost.location.GPSLocationMgr;
import com.fanglong.mobilesafe.business.lost.location.IGPSLocationMgr;
import com.fanglong.mobilesafe.business.version.IVersionUpgradeMgr;
import com.fanglong.mobilesafe.business.version.VersionUpgradeMgr;
import com.fanglong.mobilesafe.common.log.Logger;

public class ServiceFactory {
	
	/**
	 * 映射 “业务接口” 与 “业务实现类” 关系
	 */
	private final static HashMap<Class<?>, Class<?>> serviceImpMap = new HashMap<Class<?>, Class<?>>();

	static {
		serviceImpMap.put(IVersionUpgradeMgr.class, VersionUpgradeMgr.class);
		serviceImpMap.put(ILostProtectedMgr.class, LostProtectedMgr.class);
		serviceImpMap.put(IGPSLocationMgr.class, GPSLocationMgr.class);
		serviceImpMap.put(IAffilicationMgr.class, AffiliationActivity.class);
	}

	private final HashMap<Class<?>, BaseMgr> serviceMap = new HashMap<Class<?>, BaseMgr>();
	private final IAuthMgr authMgr;

	public ServiceFactory(IAuthMgr authMgr) {
		this.authMgr = authMgr;
	}

	@SuppressWarnings("unchecked")
	public synchronized <T> T getService(Class<T> clazz) {
		if (clazz.isInstance(authMgr)) {
			return (T) authMgr;
		}
		//缓存中获取对应实现业务方法对象
		BaseMgr service = serviceMap.get(clazz);
		if (clazz.isInstance(service)) {
			return (T) service;
		}
		if (service != null) {
			Logger.error("");
			return null;
		}
		try {
			//获取类对于继承类
			Class<?> implClass = serviceImpMap.get(clazz);
			//判断实现类是否继承BaseMgr
			if (implClass != BaseMgr.class && BaseMgr.class.isAssignableFrom(implClass.getClass())) {
				Logger.error(implClass + " is NOT extended from BaseMgr");
				return null;
			}
			//通过构造方法反射创建对象
			Constructor<?> constructor = implClass.getConstructor(ServiceFactory.class);
			service = (BaseMgr) constructor.newInstance(this);
			//缓存映射关系
			serviceMap.put(clazz, service);
			return (T) service;
		} catch (InstantiationException e) {
			Logger.error(e);
		} catch (IllegalAccessException e) {
			Logger.error(e);
		} catch (NoSuchMethodException e) {
			Logger.error(e);
		} catch (InvocationTargetException e) {
			Logger.error(e);
		}
		Logger.error("unknown reason to here, will return null implementation for " + clazz);
		return null;
	}

}
