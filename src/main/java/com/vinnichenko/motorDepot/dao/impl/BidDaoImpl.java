package com.vinnichenko.motorDepot.dao.impl;


import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.BidDao;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.ConnectionException;
import com.vinnichenko.motorDepot.exception.DaoException;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidDaoImpl implements BidDao {

    private static final String SQL_PENDING_BIDS = "SELECT bid_id, number_of_seats, start_date, end_date, start_point, end_point, distance, status_id FROM bids WHERE status_id = 1;";
    private static final String SQL_SELECT_BY_ID = "SELECT bid_id, number_of_seats, start_date, end_date, start_point, end_point, distance, status_id FROM bids WHERE bid_id = ?;";
    private static final String SQL_INSERT = "INSERT INTO bids (number_of_seats, start_date, end_date, start_point, end_point, distance, status_id) VALUES (?, ?, ?, ?, ?, ?, 1);";
    private static final String SQL_ASSIGN_BID = "INSERT INTO user_bids (user_id, bid_id) VALUES (?, ?);";

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public List<Bid> pendingBids() throws DaoException {
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        List<Bid> bids = new ArrayList<>();
        try {
            connection = pool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_PENDING_BIDS);
            while (resultSet.next()) {
                Bid bid = bidFromResultSet(resultSet);
                bids.add(bid);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException(e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return bids;
    }

    @Override
    public Bid findBidById(String id) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        Bid bid = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bid = bidFromResultSet(resultSet);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException("", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return bid;
    }

    @Override
    public int add(Bid bid) throws DaoException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int id = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bid.getNumber_of_seats());
            preparedStatement.setTimestamp(2, bid.getStartDate());
            preparedStatement.setTimestamp(3, bid.getEndDate());
            preparedStatement.setString(4, bid.getStartPoint());
            preparedStatement.setString(5, bid.getEndPoint());
            preparedStatement.setInt(6, bid.getDistance());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DaoException("", e);
        } finally {
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public boolean assignBid(int userId, int bidId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean result;
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_ASSIGN_BID);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bidId);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException |ConnectionException e) {
            throw new DaoException("", e);
        } finally {
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return result;
    }
}
