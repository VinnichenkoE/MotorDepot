package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.util.List;

public interface VehicleDao {
    List<Vehicle> findAllUnique() throws DaoException;

    List<Vehicle> findFreeVehiclesByNumberOfSeats(int numberOfSeats, long startDate, long endDate) throws DaoException;

    List<Vehicle> findByUserId(int userI) throws DaoException;

    List<Vehicle> findAll() throws DaoException;

    int saveVehicle(Vehicle vehicle) throws DaoException;
}
