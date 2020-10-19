package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.VihecleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;

public class WelcomePage implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VihecleService vihecleService = ServiceFactory.getInstance().getVihecleService();
        Set<Vehicle> vehicles;
        try {
            vehicles = vihecleService.findAllUnique();
            req.setAttribute(VEHICLES, vehicles);
            req.getRequestDispatcher("WEB-INF/jsp/welcome_page.jsp").forward(req, resp);
        } catch (ServiceException e) {
            req.getRequestDispatcher("WEB-INF/jsp/error_page.jsp").forward(req, resp);
        }
    }
}
