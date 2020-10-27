package com.vinnichenko.motorDepot.dao.impl;

import com.vinnichenko.motorDepot.dao.VehicleDao;
import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

public class VehicleDaoImpl implements VehicleDao {

    ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL_UNIQ = "SELECT DISTINCT vehicle_id, brand, model, registration_number, number_of_seats, status_id, user_id FROM vehicles GROUP BY brand, model, number_of_seats;";
    private static final String SQL_SELECT_FREE_VEHICLES_BY_NUMBER_SEATS = "SELECT vehicle_id, brand, model, registration_number, number_of_seats, status_id, user_id FROM vehicles WHERE number_of_seats >= ? AND status_id = 1 AND user_id NOT IN (SELECT user_id FROM user_bids ub JOIN bids b ON ub.bid_id = b.bid_id WHERE (? BETWEEN b.start_date and b.end_date) OR (? between b.start_date and b.end_date) OR (? <= b.start_date and ? >= b.end_date));";
    private static final String SQL_SELECT_BY_USER_ID = "SELECT vehicle_id, brand, model, registration_number, number_of_seats, status_id, user_id FROM vehicles WHERE user_id = ?;";
    private static final String SQL_SELECT_ALL = "SELECT vehicle_id, brand, model, registration_number, number_of_seats, status_id, user_id FROM vehicles;";
    private static final String SQL_INSERT_VEHICLE = "INSERT INTO vehicles (brand, model, registration_number, number_of_seats, status_id) VALUES (?, ? , ?, ?, ?);";

    @Override
    public List<Vehicle> findAllUnique() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            connection = pool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_UNIQ);
            while (resultSet.next()) {
                Vehicle vehicle = vehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findFreeVehiclesByNumberOfSeats(int numberOfSeats, long startDate, long endDate) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_FREE_VEHICLES_BY_NUMBER_SEATS);
            preparedStatement.setInt(1, numberOfSeats);
            preparedStatement.setLong(2, startDate);
            preparedStatement.setLong(3, endDate);
            preparedStatement.setLong(4, startDate);
            preparedStatement.setLong(5, endDate);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vehicle vehicle = vehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findByUserId(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Vehicle vehicle;
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                vehicle = vehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Vehicle vehicle;
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vehicle = vehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return vehicles;
    }

    @Override
    public int saveVehicle(Vehicle vehicle) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_VEHICLE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, vehicle.getBrand());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getRegistrationNumber());
            preparedStatement.setInt(4, vehicle.getNumberOfSeats());
            preparedStatement.setInt(5, Vehicle.VehicleStatus.NOT_ASSIGNED.getIndex());
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

    private Vehicle vehicleFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(VEHICLE_ID);
        String brand = resultSet.getString(VEHICLE_BRAND);
        String model = resultSet.getString(VEHICLE_MODEL);
        String registrationNumber = resultSet.getString(VEHICLE_REGISTRATION_NUMBER);
        int numberOfSeats = resultSet.getInt(VEHICLE_NUMBER_OF_SEATS);
        int statusId = resultSet.getInt(VEHICLE_STATUS_ID);
        int userId = resultSet.getInt(VEHICLE_USER_ID);
        Vehicle vehicle = new Vehicle(id, brand, model, registrationNumber, numberOfSeats, statusId, userId);
        return vehicle;
    }
}
