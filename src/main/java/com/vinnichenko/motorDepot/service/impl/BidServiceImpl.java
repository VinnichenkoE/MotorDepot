package com.vinnichenko.motorDepot.service.impl;

import com.vinnichenko.motorDepot.dao.BidDao;
import com.vinnichenko.motorDepot.dao.DaoFactory;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.DaoException;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.BidService;

import java.util.List;

public class BidServiceImpl implements BidService {
    @Override
    public List<Bid> pendingBids() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        BidDao bidDao = daoFactory.getBidDao();
        List<Bid> pendingBids;
        try {
            pendingBids = bidDao.pendingBids();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return pendingBids;
    }

    @Override
    public Bid findBidById(String id) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        BidDao bidDao = daoFactory.getBidDao();
        Bid bid;
        try {
            bid = bidDao.findBidById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return bid;
    }

    @Override
    public boolean addBid(int userId, Bid bid) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        BidDao bidDao = daoFactory.getBidDao();
        int id;
        boolean result;
        try {
            id = bidDao.add(bid);
            result = bidDao.assignBid(userId, id); //todo
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return result;
    }
}
