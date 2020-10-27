package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.ServiceException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface VihecleService {
    List<Vehicle> findAllUnique() throws ServiceException;
    List<Vehicle> findFreeVehiclesByNumberSeats(int numberSeats, long startDate, long endDate) throws ServiceException;
    List<Vehicle> findByUserId(int userId) throws ServiceException;
    List<Vehicle> findAll() throws ServiceException;
}
