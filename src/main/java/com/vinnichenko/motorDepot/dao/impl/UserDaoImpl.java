package com.vinnichenko.motorDepot.dao.impl;

import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.UserDao;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ConnectionException;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password, name, surname, phone_number, status_id) values (?, ? , ? , ? , ? , ?);";
    private static final String SQL_SELECT_USER_STATUS = "SELECT status_id FROM user_statuses WHERE status = ?;";
    private static final String SQL_SELECT_USER = "SELECT u.password, u.name, u.surname, u.phone_number, us.status FROM users u INNER JOIN user_statuses us ON u.status_id = us.status_id WHERE u.login = ?;";

    @Override
    public boolean saveUser(User user) throws DaoException {
        boolean result = false;
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_USER_STATUS);
            selectStatement.setString(1, user.getStatus());
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int statusId = resultSet.getInt("status_id");
                PreparedStatement insertStatement = connection.prepareStatement(SQL_INSERT_USER);
                insertStatement.setString(1, user.getLogin());
                insertStatement.setString(2, user.getPassword());
                insertStatement.setString(3, user.getName());
                insertStatement.setString(4, user.getSurname());
                insertStatement.setLong(5, user.getPhoneNumber());
                insertStatement.setInt(6, statusId);
                result = insertStatement.execute();
                connection.close();
            }
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        Connection connection;
        User user = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password"); //TODO
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                long phoneNumber = resultSet.getLong("phone_number");
                String status = resultSet.getString("status");
                user = new User(login, password, name, surname, phoneNumber, status);
            }
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }
}
