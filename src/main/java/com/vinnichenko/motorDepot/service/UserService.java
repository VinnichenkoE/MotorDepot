package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    int saveUser(Map<String, String> parameters, User.Role role) throws ServiceException;
    Optional<User> findUserByLogin(String login) throws ServiceException;
    boolean isLoginExist(String login) throws ServiceException;
    String findPassword(String login) throws ServiceException;
}
