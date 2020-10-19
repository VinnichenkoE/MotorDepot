package com.vinnichenko.motorDepot.service;

import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.ServiceException;

import java.util.List;

public interface BidService {
    List<Bid> pendingBids() throws ServiceException;
    Bid findBidById(String id) throws ServiceException;
    boolean addBid(int userId, Bid bid) throws ServiceException;
}
