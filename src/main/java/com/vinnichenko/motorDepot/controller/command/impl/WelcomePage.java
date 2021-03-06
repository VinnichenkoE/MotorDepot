package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.VihecleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.*;
import static com.vinnichenko.motorDepot.controller.command.Pages.*;

public class WelcomePage implements Command {
    private static final String DEFAULT_LANGUAGE = "ru";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.isNew()) {
            session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
        }
        VihecleService vihecleService = ServiceFactory.getInstance().getVihecleService();
        List<Vehicle> vehicles;
        try {
            vehicles = vihecleService.findAllUnique();
            req.setAttribute(VEHICLES, vehicles);
            req.getRequestDispatcher(WELCOME_PAGE).forward(req, resp);
        } catch (ServiceException e) {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
    }
}
