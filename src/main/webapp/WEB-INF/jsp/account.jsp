<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Account</title>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="i18n\messages"/>
</head>
<body>
<form action="controller" method="get">
    <input type="hidden" name="commandName" value="add_bid">
    <input type="submit" value="Оставить заявку">
</form>
<table>
    <tr>
        <th>id</th>
        <th>number of seats</th>
        <th>start date</th>
        <th>start point</th>
        <th>end point</th>
        <th>distance</th>
        <th>appoint</th>
    <tr>
        <c:forEach items="${bids}" var="bid">
    <tr>
        <td>${bid.id}</td>
        <td>${bid.number_of_seats}</td>
        <td>${bid.startDate}</td>
        <td>${bid.startPoint}</td>
        <td>${bid.endPoint}</td>
        <td>${bid.distance}</td>
        <td><form action="/controller" method="post">
            <input type="hidden" name="commandName" value="appoint_driver">
            <input type="hidden" name="id" value="${bid.id}">
            <input type="hidden" name="number_of_seats" value="${bid.number_of_seats}">
            <input type="submit" value="appoint">
        </form></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
