<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Registration</title>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="i18n\messages"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>
<jsp:include page="header.jsp"/>
<form action="/controller?commandName=save_user" method="post">
    <table class="table table-borderless">
        <tr>
            <td width="200">
                <fmt:message key="registration.enter_login"/>
            </td>
            <td align="left">
                <input required type="text" name="login"
                       placeholder="<fmt:message key="registration.login"/>" value="${parameters.login}">
            </td>
            <div class="warn">
                <td align="left" style="color: #dc3545">
                    <c:if test="${parameters.login == ''}">

                        <fmt:message key="registration.login_message"/>

                    </c:if>
                </td>
            </div>
        </tr>
        <tr>
            <td width="200">
                <fmt:message key="registration.enter_password"/>
            </td>
            <td align="left">
                <input required type="password" name="password"
                       placeholder="<fmt:message key="registration.password"/>">
            </td>
            <td align="left">
                <c:if test="${parameters.password == ''}">
                    <div class="warn">
                        <fmt:message
                                key="registration.password_message"/>
                    </div>
                </c:if>
            </td>
        </tr>
        <tr>
            <td width="200">
                <fmt:message key="registration.enter_name"/>
            </td>
            <td align="left">
                <input required type="text" name="name"
                       placeholder="<fmt:message key="registration.name"/>"
                       value="${parameters.name}">
            </td>
            <td align="left">
                <c:if test="${parameters.name == ''}">
                    <div class="warn">
                        <fmt:message key="registration.name_message"/>
                    </div>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="registration.enter_surname"/>
            </td>
            <td align="left"><input required type="text" name="surname"
                                    placeholder="<fmt:message key="registration.surname"/>"
                                    value="${parameters.surname}">
            </td>
            <td align="left">
                <c:if test="${parameters.surname == ''}">
                    <div class="warn">
                        <fmt:message key="registration.surname_message"/>
                    </div>
                </c:if>
            </td>
        <tr>
            <td>
                <fmt:message key="registration.enter_phone_number"/>
            </td>
            <td align="left"><input required type="text" name="phoneNumber"
                                    placeholder="<fmt:message key="registration.phone_number"/>"
                                    value="${parameters.phoneNumber}">
            </td>
            <td align="left">
                <c:if test="${parameters.phoneNumber == ''}">
                    <div class="warn">
                        <fmt:message key="registration.phone_number_message"/>
                    </div>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>
                <input type="hidden" name="role" value="${param.role}">
                <input type="submit" value="<fmt:message key="registration.save"/>">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
