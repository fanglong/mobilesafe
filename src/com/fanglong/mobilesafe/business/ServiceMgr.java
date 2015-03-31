package com.fanglong.mobilesafe.business;

import java.util.HashMap;

import com.fanglong.mobilesafe.application.MobileSafeApplication;
import com.fanglong.mobilesafe.business.auth.AuthMgr;
import com.fanglong.mobilesafe.business.auth.IAuthMgr;


public class ServiceMgr {
	private static Object globalServiceLocker = new Object();
	
	private static ServiceMgr globalServiceCenter = new ServiceMgr();

    public static <T> T getService(Class<T> clazz) {
        synchronized (globalServiceLocker) {
            return globalServiceCenter.getOrCreateService(clazz);
        }
    }
	
    //开启用户验证监听
	private final static IAuthMgr.AuthFailedListener listener = new IAuthMgr.AuthFailedListener() {
        @Override
        public void onAuthFailed() {
            synchronized (globalServiceLocker) {
                //暂时不清除跟用户相关的Service
            }
        }
    };
	
	private ServiceMgr() {
        authMgr = new AuthMgr(MobileSafeApplication.getGlobalContext());
        authMgr.setAuthFailedListener(listener);
    }
	
	private final IAuthMgr authMgr;
	//工厂映射 - 细粒度用户 - 服务工厂
	private final HashMap<String, ServiceFactory> serviceFactories = new HashMap<String, ServiceFactory>();
	
	@SuppressWarnings("unchecked")
    private <T> T getOrCreateService(Class<T> clazz) {
        if (clazz.isInstance(authMgr)) {
            return (T) authMgr;
        } else {
        	String uuid = "";
            ServiceFactory serviceFactory = serviceFactories.get(uuid);
            if (serviceFactory == null) {
                serviceFactory = new ServiceFactory(authMgr);
                serviceFactories.put(uuid, serviceFactory);
            }
            return serviceFactory.getService(clazz);
        }
    }
}
