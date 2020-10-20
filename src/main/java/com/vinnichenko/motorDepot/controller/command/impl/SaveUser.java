package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.SessionData;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.UserService;
import com.vinnichenko.motorDepot.validator.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.USER_DATA;

public class SaveUser implements Command {

    private static final String USER_STATUS = "клиент";
    private static final String WRONG_VALUES = "введите корректные значения";
    private static final String USER_EXIST = "пользователь с таким логином уже существует";
    private static final String LOGIN_EXIST_MESSAGE = "such login already exist";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = req.getParameter(USER_LOGIN);
        String password = req.getParameter(USER_PASSWORD);
        String name = req.getParameter(USER_NAME);
        String surname = req.getParameter(USER_SURNAME);
        String phoneNumber = req.getParameter(USER_PHONE_NUMBER);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(USER_LOGIN, login);
        parameters.put(USER_PASSWORD, password);
        parameters.put(USER_NAME, name);
        parameters.put(USER_SURNAME, surname);
        parameters.put(USER_PHONE_NUMBER, phoneNumber);
        try {
            boolean isLoginExist = userService.isLoginExist(login);
            if (DataValidator.validateRegistrationData(parameters) && !isLoginExist) {
                int id = userService.saveUser(login, password, name, surname, phoneNumber, USER_STATUS);
                if (id > 0) {
                    SessionData sessionData = new SessionData(id, name, USER_STATUS);
                    req.getSession().setAttribute(USER_DATA, sessionData);
                    resp.sendRedirect("controller?commandName=welcome_page");
                }
            } else {
                if (isLoginExist) {
                    parameters.put(USER_LOGIN, LOGIN_EXIST_MESSAGE);
                }
                req.getSession().setAttribute(PARAMETERS, parameters);
                req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
            }

        } catch (ServiceException e) {
            req.getRequestDispatcher("WEB-INF/jsp/error_page.jsp").forward(req, resp);
        }
    }
}
