package com.vinnichenko.motorDepot.service.impl;

import com.vinnichenko.motorDepot.dao.DaoFactory;
import com.vinnichenko.motorDepot.dao.VehicleDao;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.DaoException;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.VihecleService;

import java.util.Set;

public class VihecleServiceImpl implements VihecleService {
    @Override
    public Set<Vehicle> getAllUnique() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        VehicleDao vehicleDao = daoFactory.getVehicleDao();
        Set<Vehicle> vehicles;
        try {
            vehicles = vehicleDao.getAllUnique();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return vehicles;
    }
}
