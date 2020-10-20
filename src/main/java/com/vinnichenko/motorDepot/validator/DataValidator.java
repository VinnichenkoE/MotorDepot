package com.vinnichenko.motorDepot.validator;

import java.util.HashMap;
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

    private static final String WRONG_LOGIN_MESSAGE = "login isn't valid";
    private static final String WRONG_PASSWORD_MESSAGE = "password isn't valid";
    private static final String WRONG_NAME_MESSAGE = "name isn't valid";
    private static final String WRONG_SURNAME_MESSAGE = "surname isn't valid";
    private static final String WRONG_PHONE_NUMBER_MESSAGE = "phone number isn't valid";

    public static boolean isLoginValid(String login) {
        boolean result = false;
        if (login != null) {
            Pattern pattern = Pattern.compile(LOGIN_REGEX);
            Matcher matcher = pattern.matcher(login);
            result = matcher.matches();
        }
        return result;
    }

    public static boolean isPasswordValid(String password) {
        boolean result = false;
        if (password != null) {
            Pattern pattern = Pattern.compile(PASSWORD_REGEX);
            Matcher matcher = pattern.matcher(password);
            result = matcher.matches();
        }
        return result;
    }

    public static boolean isNameValid(String name) {
        boolean result = false;
        if (name != null) {
            Pattern pattern = Pattern.compile(NAME_REGEX);
            Matcher matcher = pattern.matcher(name);
            result = matcher.matches();
        }
        return result;
    }

    public static boolean isSurnameValid(String surname) {
        boolean result = false;
        if (surname != null) {
        Pattern pattern = Pattern.compile(SURNAME_REGEX);
        Matcher matcher = pattern.matcher(surname);
        result = matcher.matches();
        }
        return result;
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean result = false;
        if (phoneNumber != null) {
            Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
            Matcher matcher = pattern.matcher(phoneNumber);
            result = matcher.matches();
        }
        return result;
    }

    public static boolean isUserDataValid(String login, String password, String name, String surname, String phoneNumber) {
        boolean result = true;
        if (!isLoginValid(login) || !isPasswordValid(password)
                || !isNameValid(name) || !isSurnameValid(surname)
                || !isPhoneNumberValid(phoneNumber)) {
            result = false;
        }
        return result;
    }

    public static boolean validateRegistrationData(Map<String, String> parameters) {
        boolean result = true;
        if (!isLoginValid(parameters.get(USER_LOGIN))) {
            parameters.put(USER_LOGIN, "");
            result = false;
        }
        if (!isPasswordValid(parameters.get(USER_PASSWORD))) {
            parameters.put(USER_PASSWORD, "");
            result = false;
        }
        if (!isNameValid(parameters.get(USER_NAME))) {
            parameters.put(USER_NAME, "");
            result = false;
        }
        if (!isSurnameValid(parameters.get(USER_SURNAME))) {
            parameters.put(USER_SURNAME, "");
            result = false;
        }
        if (!isPhoneNumberValid(parameters.get(USER_PHONE_NUMBER))) {
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
