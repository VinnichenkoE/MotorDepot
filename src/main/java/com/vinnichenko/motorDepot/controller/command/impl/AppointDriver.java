package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.Bid;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.BidService;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.VihecleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.Pages.*;

public class AppointDriver implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String id = req.getParameter(ID);
        BidService bidService = ServiceFactory.getInstance().getBidService();
        VihecleService vihecleService = ServiceFactory.getInstance().getVihecleService();
        Bid bid = null;
        List<Vehicle> vehicles = null;
        try {
            bid = bidService.findBidById(id).get();
            vehicles = vihecleService.findFreeVehiclesByNumberSeats(bid.getNumber_of_seats(), bid.getStartDate(), bid.getEndDate());
        } catch (ServiceException e) {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
        req.setAttribute(VEHICLES, vehicles);
        req.setAttribute(BID, bid);
        req.getRequestDispatcher(APPOINT_DRIVER).forward(req, resp);
    }
}
