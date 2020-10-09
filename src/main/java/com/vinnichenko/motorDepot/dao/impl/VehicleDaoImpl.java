package com.vinnichenko.motorDepot.dao.impl;

import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.VehicleDao;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.ConnectionException;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

public class VehicleDaoImpl implements VehicleDao {

    ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL = "SELECT DISTINCT brand, model, number_of_seats FROM vehicles;";

    @Override
    public Set<Vehicle> getAllUnique() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Set<Vehicle> vehicles = new HashSet<>();
        try {
            connection = pool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                String brand = resultSet.getString(VEHICLE_BRAND);
                String model = resultSet.getString(VEHICLE_MODEL);
                int numberOfSeats = resultSet.getInt(VEHICLE_NUMBER_OF_SEATS);
                Vehicle vehicle = new Vehicle(brand, model, numberOfSeats);
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
}
