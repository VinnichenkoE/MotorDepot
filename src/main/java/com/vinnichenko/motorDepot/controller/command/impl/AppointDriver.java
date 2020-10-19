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

public class AppointDriver implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String id = req.getParameter(ID);
        String numberOfSeats = req.getParameter(NUMBER_OF_SEATS);
        BidService bidService = ServiceFactory.getInstance().getBidService();
        VihecleService vihecleService = ServiceFactory.getInstance().getVihecleService();
        Bid bid = null;
        List<Vehicle> vehicles = null;
        try {
            bid = bidService.findBidById(id);
            vehicles = vihecleService.getFreeVehiclesByNumberSeats(numberOfSeats);
        } catch (ServiceException e) {
            req.getRequestDispatcher("WEB-INF/jsp/error_page.jsp").forward(req, resp);
        }
        req.setAttribute(VEHICLES, vehicles);
        req.setAttribute(BID, bid);
        req.getRequestDispatcher("WEB-INF/jsp/appointDriver.jsp").forward(req, resp);
    }
}
