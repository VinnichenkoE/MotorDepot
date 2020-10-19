package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.BidService;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.validator.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;

public class SaveBid implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringId = req.getParameter(USER_ID);
        String stringNumberOfSeats = req.getParameter(NUMBER_OF_SEATS);
        String stringDistance = req.getParameter(DISTANCE);
        String startDate = req.getParameter(START_DATE);
        String endDate = req.getParameter(END_DATE);
        String startPoint = req.getParameter(START_POINT);
        String endPoint = req.getParameter(END_POINT);
        int id = 0;
        int numberOfSeats = 0;
        int distance = 0;

        startDate = startDate.replace('T', ' ');
        startDate = startDate.concat(":00");
        endDate = endDate.replace('T', ' ');
        endDate = endDate.concat(":00");
        id = Integer.parseInt(stringId);
        numberOfSeats = Integer.parseInt(stringNumberOfSeats);
        distance = Integer.parseInt(stringDistance);
        System.out.println(startDate);

        Bid bid = new Bid(numberOfSeats, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), startPoint, endPoint, distance, Bid.BidStatus.PENDING);
        BidService bidService = ServiceFactory.getInstance().getBidService();
        try {
            bidService.addBid(id, bid);
        } catch (ServiceException e) {
            req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("WEB=INF/jsp/account.jsp");

    }
}
