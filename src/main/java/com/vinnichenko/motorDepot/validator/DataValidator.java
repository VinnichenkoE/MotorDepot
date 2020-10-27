package com.vinnichenko.motorDepot.validator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;

public class DataValidator {

    private static final String LOGIN_REGEX = "[\\d\\p{LC}]{2,12}";
    private static final String PASSWORD_REGEX = "[\\d\\p{LC}]{4,16}";
    private static final String NAME_REGEX = "\\p{LC}{2,35}";
    private static final String SURNAME_REGEX = "\\p{LC}{2,50}";
    private static final String PHONE_NUMBER_REGEX = "\\d{9}";
    private static final String NUMBER_REGEX = "\\d+";

    public static boolean checkLogin(String login) {
        boolean result = false;
        if (login != null) {
            Pattern pattern = Pattern.compile(LOGIN_REGEX);
            Matcher matcher = pattern.matcher(login);
            result = matcher.matches();
        }
        return result;
    }

    public static boolean checkPassword(String password) {
        boolean result = false;
        if (password != null) {
            Pattern pattern = Pattern.compile(PASSWORD_REGEX);
            Matcher matcher = pattern.matcher(password);
            result = matcher.matches();
        }
        return result;
    }

    public static boolean checkName(String name) {
        boolean result = false;
        if (name != null) {
            Pattern pattern = Pattern.compile(NAME_REGEX);
            Matcher matcher = pattern.matcher(name);
            result = matcher.matches();
        }
        return result;
    }

    public static boolean checkSurname(String surname) {
        boolean result = false;
        if (surname != null) {
        Pattern pattern = Pattern.compile(SURNAME_REGEX);
        Matcher matcher = pattern.matcher(surname);
        result = matcher.matches();
        }
        return result;
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        boolean result = false;
        if (phoneNumber != null) {
            Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
            Matcher matcher = pattern.matcher(phoneNumber);
            result = matcher.matches();
        }
        return result;
    }

    public static boolean checkUserData(String login, String password, String name, String surname, String phoneNumber) {
        boolean result = true;
        if (!checkLogin(login) || !checkPassword(password)
                || !checkName(name) || !checkSurname(surname)
                || !checkPhoneNumber(phoneNumber)) {
            result = false;
        }
        return result;
    }

    public static boolean validateRegistrationData(Map<String, String> parameters) {
        boolean result = true;
        if (!checkLogin(parameters.get(USER_LOGIN))) {
            parameters.put(USER_LOGIN, "");
            result = false;
        }
        if (!checkPassword(parameters.get(USER_PASSWORD))) {
            parameters.put(USER_PASSWORD, "");
            result = false;
        }
        if (!checkName(parameters.get(USER_NAME))) {
            parameters.put(USER_NAME, "");
            result = false;
        }
        if (!checkSurname(parameters.get(USER_SURNAME))) {
            parameters.put(USER_SURNAME, "");
            result = false;
        }
        if (!checkPhoneNumber(parameters.get(USER_PHONE_NUMBER))) {
            parameters.put(USER_PHONE_NUMBER, "");
            result = false;
        }
        return result;
    }

    public static boolean isNumber(String number) {
        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
