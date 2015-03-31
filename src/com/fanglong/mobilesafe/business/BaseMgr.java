package com.fanglong.mobilesafe.business;

public class BaseMgr {

    private final ServiceFactory serviceFactory;

    public BaseMgr(ServiceFactory factory) {
        this.serviceFactory = factory;
    }

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }
}
