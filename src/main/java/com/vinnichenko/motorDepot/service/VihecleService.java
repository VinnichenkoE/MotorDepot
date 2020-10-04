package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.DaoException;
import com.vinnichenko.motorDepot.exception.ServiceException;

import java.util.Set;

public interface VihecleService {
    Set<Vehicle> getAllUnique() throws ServiceException;
}
