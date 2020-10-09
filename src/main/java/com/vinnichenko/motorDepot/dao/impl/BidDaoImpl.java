package com.vinnichenko.motorDepot.dao.impl;


import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.BidDao;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.ConnectionException;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

public class BidDaoImpl implements BidDao {

    private static final String SQL_PENDING_BIDS = "select bid_id, number_of_seats, start_date, start_point, end_point, distance, status from bids b inner join bid_statuses bs on b.status_id = bs.status_id where bs.status_id = 1;";

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
                int id = resultSet.getInt(BID_ID);
                int numberOfSeats = resultSet.getInt(BID_NUMBER_OF_SEATS);
                Date date = resultSet.getDate(BID_START_DATE);
                String startPoint = resultSet.getString(BID_START_POINT);
                String endPoint = resultSet.getString(BID_END_POINT);
                int distance = resultSet.getInt(BID_DISTANCE);
                String status = resultSet.getString(BID_STATUSES_STATUS);
                Bid bid = new Bid(id, numberOfSeats, date, startPoint, endPoint, distance, status);
                bids.add(bid);
                connection.close();
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
}
