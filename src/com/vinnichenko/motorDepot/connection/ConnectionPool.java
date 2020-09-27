package com.vinnichenko.motorDepot.connection;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {
    INSTANCE;

    private static final int DEFAULT_POOL_SIZE = 32;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> usedConnections;


    ConnectionPool() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //TODO
        }
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        usedConnections = new ArrayDeque<>();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            freeConnections.add(createConnection());
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();  //TODO
        }
        return connection;
    }

    public void realiseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            usedConnections.remove(connection);
            freeConnections.offer((ProxyConnection)connection);
        }
    }

    public void destroyPool() {
        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                freeConnections.take().close();
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace(); //TODO
            }
            deregisterDrivers();
        }
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace(); //TODO
            }
        });
    }

    private ProxyConnection createConnection() {
        FileInputStream fileInputStream = null;
        Properties props = new Properties();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/motor_depot?serverTimezone=UTC", "root", "root"); //TODO
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*try {
            *//*fileInputStream = new FileInputStream("src/main/resources/db.properties");
            props.load(fileInputStream);
            String url = props.getProperty("jdbc.url");
            String login = props.getProperty("jdbc.username");
            String pass = props.getProperty("jdbc.password");*//*
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/motor_depot?serverTimezone=UTC", "root", "root");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return new ProxyConnection(connection);
    }
}
