package com.vinnichenko.motorDepot.connection;

import com.vinnichenko.motorDepot.exception.ConnectionException;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public final class ConnectionPool {

    private static ConnectionPool instance;
    private static volatile boolean instanceIsCreated;

    private static final int DEFAULT_POOL_SIZE = 32;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
    private Queue<ProxyConnection> usedConnections = new ArrayDeque<>();


    private ConnectionPool() throws ConnectionException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ConnectionException("can not find driver", e);
        }
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            freeConnections.add(createConnection());
        }
    }

    public static ConnectionPool getInstance() throws ConnectionException {
        if (!instanceIsCreated) {
            synchronized (ConnectionPool.class) {
                if (!instanceIsCreated) {
                    instance = new ConnectionPool();
                    instanceIsCreated = true;
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws ConnectionException {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            throw new ConnectionException(e);
        }
        return connection;
    }

    public void realiseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            usedConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        }
    }

    public void destroyPool() throws ConnectionException {
        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException | SQLException e) {
                throw new ConnectionException(e);
            }
            deregisterDrivers();
        }
    }

    private void deregisterDrivers() throws ConnectionException {
        Iterator<Driver> iterator = DriverManager.getDrivers().asIterator();
        while (iterator.hasNext()) {
            try {
                DriverManager.deregisterDriver(iterator.next());
            } catch (SQLException e) {
                throw new ConnectionException(e);
            }
        }
    }

    private ProxyConnection createConnection() throws ConnectionException {
        Connection connection = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("db");
            String url = bundle.getString("jdbc.url");
            String login = bundle.getString("jdbc.username");
            String pass = bundle.getString("jdbc.password");
            connection = DriverManager.getConnection(url, login, pass);
        } catch (SQLException | MissingResourceException e) {
            throw new ConnectionException(e);
        }
        return new ProxyConnection(connection);
    }
}
