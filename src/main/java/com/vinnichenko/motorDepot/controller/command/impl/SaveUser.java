package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.SessionData;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveUser implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        long phoneNumber = Long.parseLong(req.getParameter("phoneNumber"));
        String status = "клиент";
        User user = new User(login, password, name, surname, phoneNumber, status);
        try {
            userService.saveUser(user);
            SessionData sessionData = new SessionData(name, status);
            req.getSession().setAttribute("data", sessionData);
            resp.sendRedirect("controller?commandName=welcome_page");
        } catch (ServiceException e) {
            resp.sendRedirect("WEB-INF/jsp/error_page.jsp");
        }

    }
}
