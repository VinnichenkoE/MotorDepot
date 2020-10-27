package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.UserSessionData;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.UserService;
import com.vinnichenko.motorDepot.validator.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.USER_DATA;
import static com.vinnichenko.motorDepot.controller.command.Pages.*;

public class SaveUser implements Command {

    private static final String LOGIN_EXIST_MESSAGE = "such login already exist";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        User.Role role;
        String roleId = req.getParameter(USER_ROLE);
        if (roleId == null || roleId.equals("")) {
            role = User.Role.CUSTOMER;
        } else {
            role = User.Role.getRole(Integer.parseInt(roleId));
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put(USER_LOGIN, req.getParameter(USER_LOGIN));
        parameters.put(USER_PASSWORD, req.getParameter(USER_PASSWORD));
        parameters.put(USER_NAME, req.getParameter(USER_NAME));
        parameters.put(USER_SURNAME, req.getParameter(USER_SURNAME));
        parameters.put(USER_PHONE_NUMBER, req.getParameter(USER_PHONE_NUMBER));
        try {
            boolean isLoginExist = userService.isLoginExist(parameters.get(USER_LOGIN));
            if (DataValidator.validateRegistrationData(parameters) && !isLoginExist) {
                int id = userService.saveUser(parameters, role);
                if (id > 0) {
                    HttpSession session = req.getSession();
                    if (session.getAttribute(USER_DATA) == null) {
                        UserSessionData sessionData = new UserSessionData(id, parameters.get(USER_NAME), role);
                        req.getSession().setAttribute(USER_DATA, sessionData);
                    }
                    resp.sendRedirect("controller?commandName=welcome_page");
                }
            } else {
                if (isLoginExist) {
                    parameters.put(USER_LOGIN, LOGIN_EXIST_MESSAGE);
                }
                req.getSession().setAttribute(PARAMETERS, parameters);
                req.getRequestDispatcher(REGISTRATION).forward(req, resp);
            }
        } catch (ServiceException e) {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
    }
}
