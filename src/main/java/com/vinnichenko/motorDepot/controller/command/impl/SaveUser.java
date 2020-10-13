package com.vinnichenko.motorDepot.controller.command.impl;

import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.entity.SessionData;
import com.vinnichenko.motorDepot.entity.User;
import com.vinnichenko.motorDepot.exception.ServiceException;
import com.vinnichenko.motorDepot.service.ServiceFactory;
import com.vinnichenko.motorDepot.service.UserService;
import com.vinnichenko.motorDepot.util.PasswordEncoder;
import com.vinnichenko.motorDepot.util.exception.UtilException;
import com.vinnichenko.motorDepot.validator.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;
import static com.vinnichenko.motorDepot.controller.command.SessionParameter.*;

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
        String stringPhoneNumber = req.getParameter(USER_PHONE_NUMBER);

        try {
            User user = userService.getUserByLogin(login);
            if (UserValidator.isUserDataValid(login, password, name, surname, stringPhoneNumber)
                    && user == null) {
                String encodingPassword = PasswordEncoder.getSaltedHash(password);
                long phoneNumber = Long.parseLong(stringPhoneNumber);
                User newUser = new User(login, encodingPassword, name, surname, phoneNumber, USER_STATUS);
                userService.saveUser(newUser);
                SessionData sessionData = new SessionData(name, USER_STATUS);
                req.getSession().setAttribute(USER_DATA, sessionData);
                resp.sendRedirect("controller?commandName=welcome_page");
            } else {
                req.setAttribute(MESSAGE, WRONG_VALUES);
                if (user != null) {
                    req.setAttribute(MESSAGE, USER_EXIST);
                }
                if (UserValidator.isLoginValid(login) && user == null) {
                    req.setAttribute(USER_LOGIN, login);
                }
                Map<String[], Boolean> validateData =
                        UserValidator.validateUserData(name, surname, stringPhoneNumber);
                Set<Map.Entry<String[], Boolean>> entrySet = validateData.entrySet();
                for (Map.Entry<String[], Boolean> el : entrySet) {
                    if (el.getValue()) {
                        req.setAttribute(el.getKey()[0], el.getKey()[1]);
                    }
                }
                req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
            }
        } catch (ServiceException |UtilException e) {
            resp.sendRedirect("WEB-INF/jsp/error_page.jsp");
        }
    }
}
