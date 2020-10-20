package com.vinnichenko.motorDepot.dao.impl;

import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.UserDao;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ConnectionException;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

public class UserDaoImpl implements UserDao {

    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password, name, surname, phone_number, status_id) values (?, ? , ? , ? , ? , ?);";
    private static final String SQL_SELECT_USER_STATUS = "SELECT status_id FROM user_statuses WHERE status = ?;";
    private static final String SQL_SELECT_USER = "SELECT u.user_id, u.password, u.name, u.surname, u.phone_number, us.status FROM users u INNER JOIN user_statuses us ON u.status_id = us.status_id WHERE u.login = ?;";
    private static final String SQL_SELECT_LOGIN = "SELECT login FROM users WHERE login = ?";

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public int saveUser(User user) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement selectStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            connection = pool.getConnection();
            selectStatement = connection.prepareStatement(SQL_SELECT_USER_STATUS);
            selectStatement.setString(1, user.getStatus());
            resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int statusId = resultSet.getInt(STATUS_ID);
                insertStatement = connection.prepareStatement(SQL_INSERT_USER);
                insertStatement.setString(1, user.getLogin());
                insertStatement.setString(2, user.getPassword());
                insertStatement.setString(3, user.getName());
                insertStatement.setString(4, user.getSurname());
                insertStatement.setString(5, user.getPhoneNumber());
                insertStatement.setInt(6, statusId);
                if (insertStatement.executeUpdate() == 0) {
                    id = -1;
                }
                ResultSet idResultSet = insertStatement.getGeneratedKeys();
                if (idResultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(selectStatement);
            pool.closeStatement(insertStatement);
            pool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString(USER_USER_ID));
                String password = resultSet.getString(USER_PASSWORD);
                String name = resultSet.getString(USER_NAME);
                String surname = resultSet.getString(USER_SURNAME);
                String phoneNumber = resultSet.getString(USER_PHONE_NUMBER);
                String status = resultSet.getString(STATUS_STATUS);
                user = new User(id, login, password, name, surname, phoneNumber, status);
            }
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return user;
    }

    @Override
    public boolean isLoginExist(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException("", e); //TODO
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return result;
    }
}
