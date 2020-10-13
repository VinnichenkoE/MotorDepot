package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.SessionData;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.UserService;
import com.vinnichenko.motorDepot.util.PasswordEncoder;
import com.vinnichenko.motorDepot.util.exception.UtilException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.*;

public class Authorization implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = req.getParameter(USER_LOGIN);
        String password  = req.getParameter(USER_PASSWORD);
        User user;
        try {
            user = userService.getUserByLogin(login);
            if (user != null && PasswordEncoder.check(password, user.getPassword())) {
                SessionData sessionData = new SessionData(user.getName(), user.getStatus());
                req.getSession().setAttribute(USER_DATA, sessionData);
            }
            resp.sendRedirect("controller?commandName=welcome_page");
        } catch (ServiceException |UtilException e) {
            resp.sendRedirect("WEB-INF/jsp/error_page.jsp");
        }
    }
}
