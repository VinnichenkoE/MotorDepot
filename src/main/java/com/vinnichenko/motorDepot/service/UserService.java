package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ServiceException;

public interface UserService {
    boolean saveUser(User user) throws ServiceException;
    User getUserByLogin(String login) throws ServiceException;
}
