package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.SQLException;

public interface UserDao {
    boolean saveUser (User user) throws SQLException, DaoException;
    User getUserByLogin(String login) throws SQLException, DaoException;
}
