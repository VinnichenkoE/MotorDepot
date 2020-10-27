package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface BidService {
    List<Bid> submittedBids() throws ServiceException;
    Optional<Bid> findBidById(String id) throws ServiceException;
    int addBid(int userId, Bid bid) throws ServiceException;
    List<Bid> findUserBids(int userId) throws ServiceException;
    boolean assignBid(int userId, int bidId) throws ServiceException;
}
