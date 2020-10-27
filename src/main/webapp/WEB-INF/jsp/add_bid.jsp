<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Add bid</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form action="controller?commandName=save_bid" method="post">
    <input type="hidden" name="user_id" value="${sessionScope.data.id}">
    <label for="number_of_seats">Количество пассажирских мест</label>
    <input type="text" name="number_of_seats" id="number_of_seats"><br>
    <label for="datetime">Ведите дату и время начала поездки</label>
    <input type="datetime-local" id="datetime" name="start_date"><br>
    <label for="endData">Введите дату и время окончания поездки</label>
    <input type="datetime-local" id="endData" name="end_date"><br>
    <label for="startPoint">Введите начальную точку маршрута</label>
    <input type="text" name="start_point" id="startPoint"><br>
    <label for="endPoint">Введите конечную точку маршрута</label>
    <input type="text" name="end_point" id="endPoint">
    <label for="distance">Введите расстояние</label><br>
    <input type="text" name="distance" id="distance">
    <input type="submit" value="Отпавить">
</form>
</body>
</html>
