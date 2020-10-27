package com.vinnichenko.motorDepot.dao.impl;

import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.UserDao;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.*;
import java.util.Optional;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

public class UserDaoImpl implements UserDao {

    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password, name, surname, phone_number, status_id) values (?, ? , ? , ? , ? , ?);";
    private static final String SQL_SELECT_USER = "SELECT user_id, login, password, name, surname, phone_number, status_id FROM users WHERE login = ?;";
    private static final String SQL_SELECT_LOGIN = "SELECT login FROM users WHERE login = ?";
    private static final String SQL_SELECT_PASSWORD = "SELECT password FROM users WHERE login = ? ;";

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public int saveUser(User user, String hashPassword) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, hashPassword);
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setInt(6, user.getRole().getIndex());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;
        Optional<User> result;
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = userFromResultSet(resultSet);
                result = Optional.of(user);
            } else {
                result = Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public boolean checkLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public String findPassword(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String password = "";
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_PASSWORD);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(USER_PASSWORD);
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return password;
    }

    private User userFromResultSet(ResultSet resultSet) throws SQLException {
        int id = Integer.parseInt(resultSet.getString(USER_USER_ID));
        String login = resultSet.getString(USER_LOGIN);
        String name = resultSet.getString(USER_NAME);
        String surname = resultSet.getString(USER_SURNAME);
        String phoneNumber = resultSet.getString(USER_PHONE_NUMBER);
        int statusId = resultSet.getInt(USER_STATUS_ID);
        User user = new User(id, login, name, surname, phoneNumber, User.Role.getRole(statusId));
        return user;
    }
}
