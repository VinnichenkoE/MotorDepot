package com.vinnichenko.motorDepot.dao.impl;

import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.BidDao;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

public class BidDaoImpl implements BidDao {

    private static final String SQL_PENDING_BIDS = "SELECT bid_id, number_of_seats, start_date, end_date, start_point, end_point, distance, status_id FROM bids WHERE status_id = 1;";
    private static final String SQL_SELECT_BY_ID = "SELECT bid_id, number_of_seats, start_date, end_date, start_point, end_point, distance, status_id FROM bids WHERE bid_id = ?;";
    private static final String SQL_INSERT = "INSERT INTO bids (number_of_seats, start_date, end_date, start_point, end_point, distance, status_id) VALUES (?, ?, ?, ?, ?, ?, 1);";
    private static final String SQL_ASSIGN_BID = "INSERT INTO user_bids (user_id, bid_id) VALUES (?, ?);";
    private static final String SQL_SELECT_USER_BIDS = "SELECT b.bid_id, b.number_of_seats, b.start_date, b.end_date, b.start_point, b.end_point, b.distance, b.status_id FROM bids b JOIN user_bids u ON b.bid_id = u.bid_id WHERE u.user_id = ?;";
    private static final String SQL_UPDATE_STATUS = "UPDATE bids SET status_id = ? WHERE bid_id = ?;";

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public List<Bid> submittedBids() throws DaoException {
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
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(statement);
            pool.releaseConnection(connection);
        }
        return bids;
    }

    @Override
    public Optional<Bid> findBidById(String id) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        Bid bid;
        Optional<Bid> result;
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bid = bidFromResultSet(resultSet);
                result = Optional.of(bid);
            } else {
                result = Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public int saveBid(Bid bid) throws DaoException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bid.getNumber_of_seats());
            preparedStatement.setLong(2, bid.getStartDate());
            preparedStatement.setLong(3, bid.getEndDate());
            preparedStatement.setString(4, bid.getStartPoint());
            preparedStatement.setString(5, bid.getEndPoint());
            preparedStatement.setInt(6, bid.getDistance());
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

    @Override
    public boolean assignBid(int userId, int bidId) throws DaoException {
        Connection connection = null;
        PreparedStatement assignStatement = null;
        PreparedStatement updateStatement = null;
        boolean result;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            assignStatement = connection.prepareStatement(SQL_ASSIGN_BID);
            updateStatement = connection.prepareStatement(SQL_UPDATE_STATUS);
            assignStatement.setInt(1, userId);
            assignStatement.setInt(2, bidId);
            result = assignStatement.executeUpdate() > 0;
            if (result) {
                updateStatement.setInt(1, Bid.BidStatus.PENDING.getIndex());
                updateStatement.setInt(2, bidId);
                result = updateStatement.executeUpdate() > 0;
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); //TODO
            }
            throw new DaoException("Error execution sql request", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
                pool.closeStatement(assignStatement);
                pool.closeStatement(updateStatement);
                pool.releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace(); //TODO
            }
        }
        return result;
    }

    @Override
    public List<Bid> findUserBids(int userId) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        Bid bid;
        List<Bid> bids = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BIDS);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bid = bidFromResultSet(resultSet);
                bids.add(bid);
            }
        } catch (SQLException e) {
            throw new DaoException("Error execution sql request", e);
        } finally {
            pool.closeResultSet(resultSet);
            pool.closeStatement(preparedStatement);
            pool.releaseConnection(connection);
        }
        return bids;
    }

    private Bid bidFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(BID_ID);
        int numberOfSeats = resultSet.getInt(BID_NUMBER_OF_SEATS);
        long startDate = resultSet.getLong(BID_START_DATE);
        long endDate = resultSet.getLong(BID_END_DATE);
        String startPoint = resultSet.getString(BID_START_POINT);
        String endPoint = resultSet.getString(BID_END_POINT);
        int distance = resultSet.getInt(BID_DISTANCE);
        int status_id = resultSet.getInt(BID_STATUS_ID);
        Bid bid = new Bid(id, numberOfSeats, startDate, endDate, startPoint, endPoint, distance, Bid.BidStatus.values()[status_id - 1]);
        return bid;
    }
}
