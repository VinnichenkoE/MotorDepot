package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.DaoException;

import static com.vinnichenko.motorDepot.dao.ColumnLabel.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface BidDao {
    List<Bid> pendingBids() throws DaoException;

    Bid findBidById(String id) throws DaoException;

    int add(Bid bid) throws DaoException;

    boolean assignBid(int userId, int bidId) throws DaoException;

    default Bid bidFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(BID_ID);
        int numberOfSeats = resultSet.getInt(BID_NUMBER_OF_SEATS);
        Timestamp startDate = resultSet.getTimestamp(BID_START_DATE);
        Timestamp endDate = resultSet.getTimestamp(BID_END_DATE);
        String startPoint = resultSet.getString(BID_START_POINT);
        String endPoint = resultSet.getString(BID_END_POINT);
        int distance = resultSet.getInt(BID_DISTANCE);
        int status_id = resultSet.getInt(BID_STATUS_ID);
        Bid bid = new Bid(id, numberOfSeats, startDate, endDate, startPoint, endPoint, distance, Bid.BidStatus.values()[status_id - 1]);
        return bid;
    }
}
