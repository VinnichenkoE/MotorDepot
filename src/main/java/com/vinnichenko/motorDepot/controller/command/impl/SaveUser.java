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
import java.util.Map;
import java.util.Set;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.USER_DATA;

public class SaveUser implements Command {

    private static final String USER_STATUS = "клиент";
    private static final String WRONG_VALUES = "введите корректные значения";
    private static final String USER_EXIST = "пользователь с таким логином уже существует";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = req.getParameter(USER_LOGIN);
        String password = req.getParameter(USER_PASSWORD);
        String name = req.getParameter(USER_NAME);
        String surname = req.getParameter(USER_SURNAME);
        String phoneNumber = req.getParameter(USER_PHONE_NUMBER);
        try {
            if (DataValidator.isLoginValid(login) && !userService.isLoginExist(login)) {
                if (userService.saveUser(login, password, name, surname, phoneNumber, USER_STATUS)) {
                    SessionData sessionData = new SessionData(3, name, USER_STATUS);
                    req.getSession().setAttribute(USER_DATA, sessionData);
                    resp.sendRedirect("controller?commandName=welcome_page");
                } else {
                    req.setAttribute(MESSAGE, WRONG_VALUES);
                    req.setAttribute(USER_LOGIN, login);
                    fillCorrectData(req, name, surname, phoneNumber);
                    req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute(MESSAGE, USER_EXIST);
                fillCorrectData(req, name, surname, phoneNumber);
                req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
            }
        } catch (ServiceException e) {
            req.getRequestDispatcher("WEB-INF/jsp/error_page.jsp").forward(req, resp);
        }
    }

    private void fillCorrectData(HttpServletRequest req, String name, String surname, String phoneNumber) {
        Map<String[], Boolean> validateData =
                DataValidator.validateUserData(name, surname, phoneNumber);
        Set<Map.Entry<String[], Boolean>> entrySet = validateData.entrySet();
        for (Map.Entry<String[], Boolean> el : entrySet) {
            if (el.getValue()) {
                req.setAttribute(el.getKey()[0], el.getKey()[1]);
            }
        }
    }
}
