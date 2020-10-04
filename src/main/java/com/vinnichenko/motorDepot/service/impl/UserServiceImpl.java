package com.vinnichenko.motorDepot.service.impl;

import com.vinnichenko.motorDepot.dao.DaoFactory;
import com.vinnichenko.motorDepot.dao.UserDao;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.DaoException;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public boolean saveUser(User user) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        boolean result = false;
        try {
            result = userDao.saveUser(user);
        } catch (SQLException | DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        User user = null;
        try {
            user = userDao.getUserByLogin(login);
        } catch (SQLException | DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }
}
