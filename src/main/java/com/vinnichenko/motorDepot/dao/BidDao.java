package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.util.List;

public interface BidDao {
    List<Bid> pendingBids() throws DaoException;
}
