package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.DaoException;

public interface UserDao {
    int saveUser (User user) throws DaoException;
    User findUserByLogin(String login) throws DaoException;
    boolean isLoginExist(String login) throws DaoException;
}
