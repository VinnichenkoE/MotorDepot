package com.vinnichenko.motorDepot.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.*;

public class UserValidator {

    private static final String LOGIN_REGEX = "[\\d\\p{LC}]{2,12}";
    private static final String PASSWORD_REGEX = "[\\d\\p{LC}]{4,16}";
    private static final String NAME_REGEX = "\\p{LC}{2,35}";
    private static final String SURNAME_REGEX = "\\p{LC}{2,50}";
    private static final String PHONE_NUMBER_REGEX = "\\d{9}";

    public static boolean isLoginValid(String login) {
        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isNameValid(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isSurnameValid(String surname) {
        Pattern pattern = Pattern.compile(SURNAME_REGEX);
        Matcher matcher = pattern.matcher(surname);
        return matcher.matches();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
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

    public static Map<String[], Boolean> validateUserData(String name, String surname, String phoneNumber) {
        Map<String[], Boolean> result = new HashMap<>();
        result.put(new String[]{USER_NAME, name}, isNameValid(name));
        result.put(new String[]{USER_SURNAME, surname}, isSurnameValid(surname));
        result.put(new String[]{USER_PHONE_NUMBER, phoneNumber}, isPhoneNumberValid(phoneNumber));
        return result;
    }

}
