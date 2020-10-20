<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="i18n\messages"/>
</head>
<body>
<nav class="bg-info" style="font-size: 150%">
    <div class="container-fluid">
        <div class="row justify-content-between">
            <div class="col-auto">
                <img  class="img" src="${pageContext.request.contextPath}/static/img/logo.png"alt="" height="150px">
            </div>
            <div class="col">
                <c:choose>
                    <c:when test="${sessionScope.data == null}">
                        <p>
                        <fmt:message key="welcome.message"/> Guest!<br>
                        <a href="controller?commandName=registration"
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
            </div>
            <div class="col-2">
                <div class="btn-group-vertical">
                    <form action="/changeLanguage" method="get">
                        <input type="hidden" name="lang" value="ru">
                        <input type="submit" value="Русский">
                    </form>
                    <form action="/changeLanguage" method="get">
                        <input type="hidden" name="lang" value="en">
                        <input type="submit" value="English">
                    </form>
                </div>
            </div>
            <div class="col-auto">
                <form action="controller?commandName=authorization" method='post'>
                    <table class="tab">
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
                            <td><input class="btn btn-primary" name="submit" type="submit" value="<fmt:message key="welcome.enter"/>"/></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</nav>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>
