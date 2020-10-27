package com.vinnichenko.motorDepot.service.impl;

import com.vinnichenko.motorDepot.dao.DaoFactory;
import com.vinnichenko.motorDepot.dao.VehicleDao;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.DaoException;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.VihecleService;

import java.util.List;

public class VihecleServiceImpl implements VihecleService {
    @Override
    public List<Vehicle> findAllUnique() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        VehicleDao vehicleDao = daoFactory.getVehicleDao();
        List<Vehicle> vehicles;
        try {
            vehicles = vehicleDao.findAllUnique();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findFreeVehiclesByNumberSeats(int numberSeats, long startDate, long endDate) throws ServiceException {
        VehicleDao dao = DaoFactory.getInstance().getVehicleDao();
        List<Vehicle> vehicles;
        try {
            vehicles = dao.findFreeVehiclesByNumberOfSeats(numberSeats, startDate, endDate);
        } catch (DaoException e) {
            throw new ServiceException("", e); //TODO
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findByUserId(int userId) throws ServiceException {
        VehicleDao vehicleDao = DaoFactory.getInstance().getVehicleDao();
        List<Vehicle> vehicles;
        try {
            vehicles = vehicleDao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findAll() throws ServiceException {
        VehicleDao vehicleDao = DaoFactory.getInstance().getVehicleDao();
        List<Vehicle> vehicles;
        try {
            vehicles = vehicleDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return vehicles;
    }
}
