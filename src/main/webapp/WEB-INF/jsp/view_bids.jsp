<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="date" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title>Bids</title>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="i18n\messages"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container-fluid">
    <div class="row">
        <div class="col-auto">
            <c:if test="${data.role.getIndex() == 3}">
                <form action="controller" method="get">
                    <input type="hidden" name="commandName" value="add_bid">
                    <input type="submit" value="<fmt:message key="account.add_bid"/>">
                </form>
            </c:if>
        </div>
    </div>
    <div class="row">
        <table>
            <tr>
                <th>id</th>
                <th>number of seats</th>
                <th>start date</th>
                <th>end date</th>
                <th>start point</th>
                <th>end point</th>
                <th>distance</th>
            <tr>
                <c:forEach items="${bids}" var="bid">
            <tr>
                <td>${bid.id}</td>
                <td>${bid.number_of_seats}</td>
                <td><date:todate date="${bid.startDate}"/></td>
                <td><date:todate date="${bid.endDate}"/></td>
                <td>${bid.startPoint}</td>
                <td>${bid.endPoint}</td>
                <td>${bid.distance}</td>
                <td>
                    <c:if test="${data.role.getIndex() == 1}">
                        <form action="/controller" method="post">
                            <input type="hidden" name="commandName" value="appoint_driver">
                            <input type="hidden" name="id" value="${bid.id}">
                            <input type="submit" value="appoint">
                        </form>
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>
