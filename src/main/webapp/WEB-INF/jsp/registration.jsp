<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Registration</title>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="i18n\messages"/>
</head>
<div align="right">
    <a href="/changeLanguage?lang=ru">ru </a>
    <a href="/changeLanguage?lang=en">en</a>
</div>
<h4 style="color: red">${requestScope.message}</h4>
<form action="/controller" method="post">
    <table class="table table-borderless">
        <tr>
            <td width="200"><fmt:message key="registration.enter_login"/></td>
            <td align="left"><input required type="text" name="login"
                                    placeholder=
                                    <fmt:message
                                            key="registration.login"/> <%--pattern="[\dA-Za-zА-Яа-яЁё]{4,8}"--%>
                                            value="${login}"></td>
            <td align="left"><fmt:message key="registration.login_message"/>
            </td>
        </tr>
        <tr>
            <td width="200"><fmt:message
                    key="registration.enter_password"/></td>
            <td align="left"><input required type="password" name="password"
                                    placeholder="<fmt:message key="registration.password"/>">
            </td>
            <td align="left"><fmt:message
                    key="registration.password_message"/></td>
        </tr>
        <tr>
            <td width="200"><fmt:message key="registration.enter_name"/></td>
            <td align="left"><input required type="text" name="name"
                                    placeholder="<fmt:message key="registration.name"/>"
                                    value="${name}"></td>
            <td align="left"><fmt:message key="registration.name_message"/></td>
        </tr>
        <tr>
            <td><fmt:message key="registration.enter_surname"/></td>
            <td align="left"><input required type="text" name="surname"
                                    placeholder="<fmt:message key="registration.surname"/>"
                                    value="${surname}">
            </td>
            <td align="left"><fmt:message
                    key="registration.surname_message"/></td>
        <tr>
            <td><fmt:message key="registration.enter_phone_number"/></td>
            <td align="left"><input required type="text" name="phoneNumber"
                                    placeholder="<fmt:message key="registration.phone_number"/>"
                                    value="${phoneNumber}"></td>
            <td align="left"><fmt:message
                    key="registration.phone_number_message"/></td>
        </tr>
        <tr>
            <td><input type="hidden" name="commandName" value="save_user">
        </tr>
        <tr>
            <td><input type="submit"
                       value="<fmt:message key="registration.save"/>"></td>
        </tr>
    </table>
</form>
</body>
</html>
