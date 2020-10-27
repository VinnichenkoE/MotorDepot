package com.vinnichenko.motorDepot.service.impl;

import com.vinnichenko.motorDepot.dao.BidDao;
import com.vinnichenko.motorDepot.dao.DaoFactory;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.DaoException;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.BidService;

import java.util.List;
import java.util.Optional;

public class BidServiceImpl implements BidService {
    @Override
    public List<Bid> submittedBids() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        BidDao bidDao = daoFactory.getBidDao();
        List<Bid> pendingBids;
        try {
            pendingBids = bidDao.submittedBids();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return pendingBids;
    }

    @Override
    public Optional<Bid> findBidById(String id) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        BidDao bidDao = daoFactory.getBidDao();
        Optional<Bid> bid;
        try {
            bid = bidDao.findBidById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return bid;
    }

    @Override
    public int addBid(int userId, Bid bid) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        BidDao bidDao = daoFactory.getBidDao();
        int id;
        boolean result;
        try {
            id = bidDao.saveBid(bid);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return id;
    }

    @Override
    public List<Bid> findUserBids(int userId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        BidDao bidDao = daoFactory.getBidDao();
        List<Bid> bids;
        try {
            bids = bidDao.findUserBids(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return bids;
    }

    @Override
    public boolean assignBid(int userId, int bidId) throws ServiceException {
        BidDao bidDao = DaoFactory.getInstance().getBidDao();
        boolean result = false;
        try {
            result = bidDao.assignBid(userId, bidId);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return result;
    }
}
