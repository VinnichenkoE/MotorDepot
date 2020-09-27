package com.vinnichenko.motorDepot.dao;

import com.vinnichenko.motorDepot.entity.Bid;

import java.util.List;

public interface BidDao {
    List<Bid> pendingBids();
}
