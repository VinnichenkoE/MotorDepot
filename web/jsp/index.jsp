<%--
  Created by IntelliJ IDEA.
  User: e-lin
  Date: 27.09.2020
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <th>id</th>
        <th>number_of_seats</th>
        <th>start_date</th>
        <th>start_point</th>
        <th>end_point</th>
        <th>distance</th>
        <th>status</th>
    </tr>
    <tbody>
    <c:forEach items="${bids}" var="bid">

        <tr>
            <td>${bid.getId()}</td>
            <td>${bid.getNumber_of_seats()}</td>
            <td>${bid.getStartDate()}</td>
            <td>${bid.getStartPoint()}</td>
            <td>${bid.getEndPoint()}</td>
            <td>${bid.getDistance()}</td>
            <td>${bid.getStatus()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
