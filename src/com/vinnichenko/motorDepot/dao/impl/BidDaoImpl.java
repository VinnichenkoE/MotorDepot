package com.vinnichenko.motorDepot.dao.impl;



import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.dao.BidDao;
import com.vinnichenko.motorDepot.entity.Bid;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidDaoImpl implements BidDao {

    private static final String SQL_PENDING_BIDS = "select bid_id, number_of_seats, start_date, start_point, end_point, distance, status from bids b inner join bid_statuses bs on b.status_id = bs.status_id where bs.status_id = 1;";

    @Override
    public List<Bid> pendingBids() {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Bid> bids = new ArrayList<>();
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_PENDING_BIDS);
            while (resultSet.next()) {
                int id = resultSet.getInt("bid_id");  //TODO
                int numberOfSeats = resultSet.getInt("number_of_seats");
                Date date = resultSet.getDate("start_date");
                String startPoint = resultSet.getString("start_point");
                String endPoint = resultSet.getString("end_point");
                int distance = resultSet.getInt("distance");
                String status = resultSet.getString("status");
                Bid bid = new Bid(id, numberOfSeats, date, startPoint, endPoint, distance, status);
                bids.add(bid);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //TODO
        }
        return bids;
    }
}
