package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.dao.BidDao;
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
import java.util.Set;

public class WelcomePage implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VihecleService vihecleService = ServiceFactory.getInstance().getVihecleService();
        Set<Vehicle> vehicles;
        try {
            vehicles = vihecleService.getAllUnique();
            req.setAttribute("vehicles", vehicles);
            req.getRequestDispatcher("WEB-INF/jsp/welcome_page.jsp").forward(req, resp);
        } catch (ServiceException e) {
            resp.sendRedirect("WEB-INF/jsp/error_page.jsp");
        }

    }
}
