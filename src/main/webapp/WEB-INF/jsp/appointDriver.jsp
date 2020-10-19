<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
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
        <td>${bid.startPoint}</td>
        <td>${bid.endPoint}</td>
        <td>${bid.distance}</td>
        <td><select>
            <c:forEach items="${vehicles}" var="vehicle">
                <option>${vehicle.getModel()}</option>
            </c:forEach>
        </select></td>
    </tr>
</table>
</body>
</html>
