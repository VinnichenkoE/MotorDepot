<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<table>
    <tr>
        <th>id</th>
        <th>number of seats</th>
        <th>start date</th>
        <th>start point</th>
        <th>end point</th>
        <th>distance</th>
        <th>appoint</th>
    </tr>
    <tr>
        <td>${bid.id}</td>
        <td>${bid.number_of_seats}</td>
        <td>${bid.startDate}</td>
        <td>${bid.endDate}</td>
        <td>${bid.startPoint}</td>
        <td>${bid.endPoint}</td>
        <td>${bid.distance}</td>
        <td>
            <form action="controller" method="post">
                <select name="user_id">
                    <c:forEach items="${vehicles}" var="vehicle">
                        <option value="${vehicle.getUser_id()}">${vehicle.getBrand()} ${vehicle.getModel()} мест: ${vehicle.getNumberOfSeats()}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="commandName" value="appoint_bid">
                <input type="hidden" name="bid_id" value="${bid.id}">
                <input type="submit" value="appoint">
            </form>
        </td>
    </tr>
</table>
</body>
</html>
