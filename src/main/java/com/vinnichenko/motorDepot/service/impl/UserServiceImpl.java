package com.vinnichenko.motorDepot.service.impl;

import com.vinnichenko.motorDepot.dao.DaoFactory;
import com.vinnichenko.motorDepot.dao.UserDao;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.DaoException;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.UserService;
import com.vinnichenko.motorDepot.util.PasswordEncoder;
import com.vinnichenko.motorDepot.util.exception.UtilException;
import com.vinnichenko.motorDepot.validator.DataValidator;

import java.util.Map;
import java.util.Optional;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;

public class UserServiceImpl implements UserService {
    @Override
    public int saveUser(Map<String, String> parameters, User.Role role) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        int id = 0;
        User user;
        try {
            String encodingPassword = PasswordEncoder.getSaltedHash(parameters.get(USER_PASSWORD));
            user = new User(parameters.get(USER_LOGIN), parameters.get(USER_NAME),
                    parameters.get(USER_SURNAME), parameters.get(USER_PHONE_NUMBER), role);
            id = userDao.saveUser(user, encodingPassword);
        } catch (UtilException | DaoException e) {
            throw new ServiceException("", e); //TODO
        }
        return id;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Optional<User> user;
        try {
            user = userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("", e); //TODO
        }
        return user;
    }

    @Override
    public boolean isLoginExist(String login) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        boolean result;
        try {
            result = userDao.checkLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("", e); //TODO
        }
        return result;
    }

    @Override
    public String findPassword(String login) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        String password = null;
        try {
            password = userDao.findPassword(login);
        } catch (DaoException e) {
            throw new ServiceException("", e);  //TODO
        }
        return password;
    }
}
