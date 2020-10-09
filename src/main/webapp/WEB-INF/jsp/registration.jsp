<%--
  Created by IntelliJ IDEA.
  User: e-lin
  Date: 03.10.2020
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<h4 style="color: red">${requestScope.message}</h4>
<form action="/controller" method="post">
    <table class="table table-borderless">
        <tr>
            <td width="200">Введите Логин</td>
            <td align="left"><input required type="text" name="login"
                                    placeholder="Логин" <%--pattern="[\dA-Za-zА-Яа-яЁё]{4,8}"--%>
                                    value="${login}"></td>
            <td align="left"><small>цифры или буквы от 2 до 12 символов</small>
            </td>
        </tr>
        <tr>
            <td width="200">Введите пароль</td>
            <td align="left"><input required type="password" name="password"
                                    placeholder="Пароль"></td>
            <td align="left">цифры или буквы от 4 до 16 символов</td>
        </tr>
        <tr>
            <td width="200">Введите имя</td>
            <td align="left"><input required type="text" name="name"
                                    placeholder="Имя" value="${name}"></td>
            <td align="left">буквы от 2 до 35 символов</td>
        </tr>
        <tr>
            <td>Введите фамилию</td>
            <td align="left"><input required type="text" name="surname"
                                    placeholder="Фамилия" value="${surname}">
            </td>
            <td align="left">буквы от 2 до 50 символов</td>
        <tr>
            <td>Введите номер телефона</td>
            <td align="left"><input required type="text" name="phoneNumber"
                                    placeholder="Номер телефона"
                                    value="${phoneNumber}"></td>
            <td align="left">9 цифр</td>
        </tr>
        <tr>
            <td><input type="hidden" name="commandName" value="save_user">
        </tr>
        <tr>
            <td><input type="submit" value="Сохранить"></td>
        </tr>
    </table>
</form>
</body>
</html>
