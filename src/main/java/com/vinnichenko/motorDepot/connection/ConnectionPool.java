package com.vinnichenko.motorDepot.connection;

import com.vinnichenko.motorDepot.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public final class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance = new ConnectionPool();

    private static final int DEFAULT_POOL_SIZE = 32;
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc.url";
    private static final String DATABASE_USERNAME = "jdbc.username";
    private static final String DATABASE_PASSWORD = "jdbc.password";
    private static final String RESOURCE_NAME = "db";


    private BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
    private Queue<ProxyConnection> usedConnections = new ArrayDeque<>();


    private ConnectionPool() {
        try {
            Class.forName(DATABASE_DRIVER);
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                freeConnections.add(createConnection());
            }
        } catch (ClassNotFoundException | ConnectionException e) {
            logger.fatal("Error connecting to database");
            throw new RuntimeException("Error connecting to database");
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Connection pool can not provide connection", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            if (usedConnections.remove(connection)) {
                freeConnections.offer((ProxyConnection) connection);
            }
        }
    }

    public void destroyPool() {
        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                freeConnections.take().reallyClose();
                deregisterDrivers();
            } catch (InterruptedException | SQLException e) {
                logger.error("error during destruction pool", e);
            }

        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("can't close statement");
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("can't close statement");
            }
        }
    }

    private void deregisterDrivers() throws SQLException {
        Iterator<Driver> iterator = DriverManager.getDrivers().asIterator();
        while (iterator.hasNext()) {
            DriverManager.deregisterDriver(iterator.next());
        }
    }

    private ProxyConnection createConnection() throws ConnectionException {
        Connection connection;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_NAME);
            String url = bundle.getString(DATABASE_URL);
            String login = bundle.getString(DATABASE_USERNAME);
            String pass = bundle.getString(DATABASE_PASSWORD);
            connection = DriverManager.getConnection(url, login, pass);
        } catch (SQLException e) {
            throw new ConnectionException("can not create connection", e);
        } catch (MissingResourceException e) {
            throw new ConnectionException("object for the given key can not be found", e);
        }
        return new ProxyConnection(connection);
    }
}
