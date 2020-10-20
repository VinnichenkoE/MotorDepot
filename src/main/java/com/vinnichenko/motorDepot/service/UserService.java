package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ServiceException;

public interface UserService {
    int saveUser(String login, String password, String name, String surname, String phoneNumber, String status) throws ServiceException;
    User findUserByLogin(String login) throws ServiceException;
    boolean isLoginExist(String login) throws ServiceException;
}
