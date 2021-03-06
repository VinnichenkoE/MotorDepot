package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.service.impl.BidServiceImpl;
import com.vinnichenko.motorDepot.service.impl.UserServiceImpl;
import com.vinnichenko.motorDepot.service.impl.VihecleServiceImpl;

public final class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final BidService bidService = new BidServiceImpl();
    private final VihecleService vihecleService = new VihecleServiceImpl();


    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public BidService getBidService() {
        return bidService;
    }

    public VihecleService getVihecleService() {
        return vihecleService;
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }
}
