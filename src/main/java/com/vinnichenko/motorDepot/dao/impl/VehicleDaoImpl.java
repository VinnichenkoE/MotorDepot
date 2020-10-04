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

public class VehicleDaoImpl implements VehicleDao {

    private static final String SQL_SELECT_ALL = "SELECT DISTINCT brand, model, number_of_seats FROM vehicles;";

    @Override
    public Set<Vehicle> getAllUnique() throws DaoException {
        Statement statement;
        ResultSet resultSet;
        Set<Vehicle> vehicles = new HashSet<>();
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int numberOfSeats = resultSet.getInt("number_of_seats");
                Vehicle vehicle = new Vehicle(brand, model, numberOfSeats);
                vehicles.add(vehicle);
                connection.close();
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        }
        return vehicles;
    }
}
