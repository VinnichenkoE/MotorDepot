package com.vinnichenko.motorDepot.service.impl;

import com.vinnichenko.motorDepot.dao.DaoFactory;
import com.vinnichenko.motorDepot.dao.VehicleDao;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.DaoException;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.VihecleService;
import com.vinnichenko.motorDepot.validator.DataValidator;

import java.util.List;
import java.util.Set;

public class VihecleServiceImpl implements VihecleService {
    @Override
    public Set<Vehicle> findAllUnique() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        VehicleDao vehicleDao = daoFactory.getVehicleDao();
        Set<Vehicle> vehicles;
        try {
            vehicles = vehicleDao.findAllUnique();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getFreeVehiclesByNumberSeats(String stringNumberSeats) throws ServiceException {
        if (!DataValidator.isNumber(stringNumberSeats)) {
            throw new ServiceException("invalid value");
        }
        int numberOfSeats = Integer.parseInt(stringNumberSeats);
        VehicleDao dao = DaoFactory.getInstance().getVehicleDao();
        List<Vehicle> vehicles;
        try {
            vehicles = dao.findFreeVehiclesByNumberOfSeats(numberOfSeats);
        } catch (DaoException e) {
            throw new ServiceException("", e); //TODO
        }
        return vehicles;
    }
}
