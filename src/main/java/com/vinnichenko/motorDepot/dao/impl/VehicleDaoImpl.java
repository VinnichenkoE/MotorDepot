package com.vinnichenko.motorDepot.dao.impl;

import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.VehicleDao;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.ConnectionException;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

public class VehicleDaoImpl implements VehicleDao {

    ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL = "SELECT DISTINCT vehicle_id, brand, model, registration_number, number_of_seats, status_id, user_id FROM vehicles GROUP BY brand, model, number_of_seats;";
    private static final String SQL_SELECT_FREE_VEHICLES_BY_NUMBER_SEATS = "SELECT vehicle_id, brand, model, registration_number, number_of_seats, status_id, user_id FROM vehicles WHERE number_of_seats > ? AND status_id = 1;";

    @Override
    public Set<Vehicle> findAllUnique() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Set<Vehicle> vehicles = new HashSet<>();
        try {
            connection = pool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Vehicle vehicle = vehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findFreeVehiclesByNumberOfSeats(int numberOfSeats) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_FREE_VEHICLES_BY_NUMBER_SEATS);
            preparedStatement.setInt(1, numberOfSeats);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vehicle vehicle = vehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return vehicles;
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
