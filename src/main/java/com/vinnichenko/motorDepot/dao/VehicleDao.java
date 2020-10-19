package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.util.List;
import java.util.Set;

public interface VehicleDao {
    Set<Vehicle> findAllUnique() throws DaoException;
    List<Vehicle> findFreeVehiclesByNumberOfSeats(int numberOfSeats) throws DaoException;
}
