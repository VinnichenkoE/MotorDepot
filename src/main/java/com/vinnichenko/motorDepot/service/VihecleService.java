package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface VihecleService {
    Set<Vehicle> findAllUnique() throws ServiceException;
    List<Vehicle> getFreeVehiclesByNumberSeats(String stringNumberSeats) throws ServiceException;
}
