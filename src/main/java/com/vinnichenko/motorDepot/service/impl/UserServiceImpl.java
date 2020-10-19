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

public class UserServiceImpl implements UserService {
    @Override
    public boolean saveUser(String login, String password, String name, String surname, String phoneNumber, String status) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        boolean result = false;
        User user;
        if (DataValidator.isUserDataValid(login, password, name, surname, phoneNumber)) {
            try {
                String encodingPassword = PasswordEncoder.getSaltedHash(password);
                user = new User(login, encodingPassword, name, surname, phoneNumber, status);
                result = userDao.saveUser(user);
            } catch (UtilException | DaoException e) {
                throw new ServiceException("", e); //TODO
            }
        }
        return result;
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        User user;
        try {
            user = userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean isLoginExist(String login) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        boolean result;
        try {
            result = userDao.isLoginExist(login);
        } catch (DaoException e) {
            throw new ServiceException("", e); //TODO
        }
        return result;
    }
}
