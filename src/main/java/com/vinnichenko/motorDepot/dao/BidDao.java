package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BidDao {
    List<Bid> submittedBids() throws DaoException;

    Optional<Bid> findBidById(String id) throws DaoException;

    int saveBid(Bid bid) throws DaoException;

    boolean assignBid(int userId, int bidId) throws DaoException;

    List<Bid> findUserBids(int userId) throws DaoException;
}
