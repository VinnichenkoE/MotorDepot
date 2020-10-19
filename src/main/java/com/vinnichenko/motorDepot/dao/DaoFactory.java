package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.dao.impl.BidDaoImpl;
import com.vinnichenko.motorDepot.dao.impl.UserDaoImpl;
import com.vinnichenko.motorDepot.dao.impl.VehicleDaoImpl;

public final class DaoFactory {
    
    private static final DaoFactory INSTANCE = new DaoFactory();

    private final UserDao userDao = new UserDaoImpl();
    private final BidDao bidDao = new BidDaoImpl();
    private final VehicleDao vehicleDao = new VehicleDaoImpl();

    private DaoFactory() {
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public BidDao getBidDao() {
        return bidDao;
    }

    public VehicleDao getVehicleDao() {
        return vehicleDao;
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }
}
