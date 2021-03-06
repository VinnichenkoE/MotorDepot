<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Account</title>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="i18n\messages"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<nav>
    <div class="container-fluid">
        <div class="row">
            <div class="col-auto">
                <form action="controller" method="get">
                    <input type="hidden" name="commandName" value="view_bids">
                    <input type="submit" value="<fmt:message key="account.my_bids"/>">
                </form>
            </div>
            <div class="col-auto">
                <c:if test="${data.role.getIndex() <= 2}">
                    <form action="controller" method="get">
                        <input type="hidden" name="commandName" value="view_vehicles">
                        <input type="submit" value="<fmt:message key="account.vehicles"/>">
                    </form>
                </c:if>
            </div>
            <div class="col-auto">
                <c:if test="${data.role.getIndex() == 1}">
                    <form action="controller" method="get">
                        <input type="hidden" name="commandName" value="view_drivers">
                        <input type="submit" value="<fmt:message key="account.driver"/>">
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</nav>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
