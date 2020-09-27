package com.vinnichenko.motorDepot.controller;

import com.vinnichenko.motorDepot.dao.BidDao;
import com.vinnichenko.motorDepot.dao.BidDaoFactory;
import com.vinnichenko.motorDepot.entity.Bid;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "mainServlet", urlPatterns = "/")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BidDao bidDao = BidDaoFactory.getInstance().getBidDao();
        List<Bid> bids = bidDao.pendingBids();
        req.setAttribute("bids", bids);
        req.getRequestDispatcher("jsp/index.jsp").forward(req, resp);
    }
}
