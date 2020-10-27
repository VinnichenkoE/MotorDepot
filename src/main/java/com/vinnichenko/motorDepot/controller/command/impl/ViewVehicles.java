package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.entity.UserSessionData;
import com.vinnichenko.motorDepot.entity.Vehicle;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.VihecleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.*;
import static com.vinnichenko.motorDepot.controller.command.Pages.*;

public class ViewVehicles implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserSessionData data = (UserSessionData) req.getSession().getAttribute(USER_DATA);
        List<Vehicle> vehicles;
        VihecleService vihecleService = ServiceFactory.getInstance().getVihecleService();
        try {
            if (data.getRole().equals(User.Role.DISPATCHER)) {
                vehicles = vihecleService.findAll();
            } else {
                vehicles = vihecleService.findByUserId(data.getId());
            }
            req.setAttribute(VEHICLES, vehicles);
            req.getRequestDispatcher(VIEW_VEHICLES).forward(req, resp);
        } catch (ServiceException e) {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
    }
}
