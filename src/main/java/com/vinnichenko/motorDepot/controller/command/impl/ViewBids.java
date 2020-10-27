package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.entity.UserSessionData;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.BidService;
import com.vinnichenko.motorDepot.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.*;
import static com.vinnichenko.motorDepot.controller.command.Pages.*;

public class ViewBids implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BidService bidService = ServiceFactory.getInstance().getBidService();
        UserSessionData data = (UserSessionData) req.getSession().getAttribute(USER_DATA);
        List<Bid> bids;
        try {
            if (data.getRole().equals(User.Role.DISPATCHER)) {
                bids = bidService.submittedBids();
            } else {
                bids = bidService.findUserBids(data.getId());
            }
            req.setAttribute(BIDS, bids);
            req.getRequestDispatcher(VIEW_BIDS).forward(req, resp);
        } catch (ServiceException e) {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
    }
}
