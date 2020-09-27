package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.dao.impl.BidDaoImpl;

public final class BidDaoFactory {

    private static final BidDaoFactory instance = new BidDaoFactory();

    private final BidDao bidDao = new BidDaoImpl();

    private BidDaoFactory() {}

    public BidDao getBidDao() {
        return bidDao;
    }

    public static BidDaoFactory getInstance() {
        return instance;
    }
}
