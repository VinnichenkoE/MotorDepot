package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.BidService;
import com.vinnichenko.motorDepot.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;

public class Account implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BidService bidService = ServiceFactory.getInstance().getBidService();
        try {
            List<Bid> bids = bidService.pendingBids();
            req.setAttribute(BIDS, bids);
            req.getRequestDispatcher("WEB-INF/jsp/account.jsp").forward(req, resp);
        } catch (ServiceException e) {
            req.getRequestDispatcher("WEB-INF/jsp/error_page.jsp").forward(req, resp);
        }
    }
}
