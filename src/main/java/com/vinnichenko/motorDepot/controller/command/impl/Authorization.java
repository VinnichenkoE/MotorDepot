package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.UserSessionData;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.UserService;
import com.vinnichenko.motorDepot.util.PasswordEncoder;
import com.vinnichenko.motorDepot.util.exception.UtilException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.*;
import static com.vinnichenko.motorDepot.controller.command.Pages.*;

public class Authorization implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = req.getParameter(USER_LOGIN);
        String password  = req.getParameter(USER_PASSWORD);
        Optional<User> user;
        String savedPassword;
        try {
            user = userService.findUserByLogin(login);
            savedPassword = userService.findPassword(login);
            if (user.isPresent() && PasswordEncoder.check(password, savedPassword)) {
                UserSessionData sessionData = new UserSessionData(user.get().getId(), user.get().getName(), user.get().getRole());
                req.getSession().setAttribute(USER_DATA, sessionData);
            }
            resp.sendRedirect("controller?commandName=welcome_page");
        } catch (ServiceException | UtilException e) {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
    }
}
