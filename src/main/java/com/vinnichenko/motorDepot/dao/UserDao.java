package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    int saveUser(User user, String hashPassword) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    boolean checkLogin(String login) throws DaoException;

    String findPassword(String login) throws DaoException;
}

