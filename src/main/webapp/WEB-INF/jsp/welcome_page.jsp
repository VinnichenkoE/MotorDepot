<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Welcome Page</title>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="i18n\messages"/>
</head>
<body>
<%--<c:choose>
    <c:when test="${sessionScope.data == null}">
        <p>
        <fmt:message key="welcome.message"/> Guest!
        <br><a href="controller?commandName=registration"
               \><fmt:message key="welcome.registration"/></a>
    </c:when>
    <c:when test="${sessionScope.data != null}">
        <fmt:message key="welcome.message"/> ${data.name}
        <form action="/controller" method="get">
            <input type="hidden" name="commandName" value="account">
            <input type="submit" value="<fmt:message key="welcome.account"/>">
        </form>
        <br><a href="controller?commandName=logout">Выйти</a>
    </c:when>
</c:choose>
<div align="right">
    <a href="/changeLanguage?lang=ru">ru </a>
    <a href="/changeLanguage?lang=en">en</a>
</div>
<h4><fmt:message key="welcome.enter_login"/></h4>
<form action="controller?commandName=authorization" method='post'>
    <table>
        <tr>
            <td><fmt:message key="welcome.login"/></td>
            <td><input type='text' name='login'
                       placeholder="<fmt:message key="welcome.login"/>">
            </td>
        </tr>
        <tr>
            <td><fmt:message key="welcome.password"/></td>
            <td><input type="password" name='password'
                       placeholder="<fmt:message key="welcome.password"/>"/>
            </td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Войти"/></td>
        </tr>
    </table>
</form>
</div>--%>
<jsp:include page="header.jsp"/>
<h3>
    <fmt:message key="welcome.our_cars"/>
</h3>
<table>
    <tr>
        <th align="center"><fmt:message key="welcome.brand"/></th>
        <th align="center"><fmt:message key="welcome.model"/></th>
        <th align="center"><fmt:message key="welcome.number_of_seats"/></th>
    </tr>
    <tbody>
    <c:forEach items="${vehicles}" var="vehicle">
        <tr>
            <td align="center">${vehicle.getBrand()}</td>
            <td align="center">${vehicle.getModel()}</td>
            <td align="center">${vehicle.getNumberOfSeats()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
