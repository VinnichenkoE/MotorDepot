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
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>
