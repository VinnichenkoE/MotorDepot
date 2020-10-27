package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.BidService;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.util.DateConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.Pages.*;

public class SaveBid implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringId = req.getParameter(USER_ID);
        String stringNumberOfSeats = req.getParameter(NUMBER_OF_SEATS);
        String stringDistance = req.getParameter(DISTANCE);
        String stringStartDate = req.getParameter(START_DATE);
        String stringEndDate = req.getParameter(END_DATE);
        String startPoint = req.getParameter(START_POINT);
        String endPoint = req.getParameter(END_POINT);

        long startDate = DateConverter.toLong(stringStartDate);
        long endDate = DateConverter.toLong(stringEndDate);
        int id = Integer.parseInt(stringId);
        int numberOfSeats = Integer.parseInt(stringNumberOfSeats);
        int distance = Integer.parseInt(stringDistance);

        Bid bid = new Bid(numberOfSeats, startDate, endDate, startPoint, endPoint, distance, Bid.BidStatus.SUBMITTED);
        BidService bidService = ServiceFactory.getInstance().getBidService();
        try {
            bidService.addBid(id, bid);
        } catch (ServiceException e) {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
        resp.sendRedirect("controller?commandName=account");
    }
}
