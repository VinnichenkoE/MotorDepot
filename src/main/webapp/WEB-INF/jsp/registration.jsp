<%--
  Created by IntelliJ IDEA.
  User: e-lin
  Date: 03.10.2020
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="/controller" method="post">
    <table class="table table-borderless">
        <tr>
            <td width="200">Введите Логин</td>
            <td align="left"><input required type="text" name="login" placeholder="Логин"></td>
        </tr>
        <tr>
            <td width="200">Введите пароль</td>
            <td align="left"><input required type="password" name="password" placeholder="Пароль"></td>
        </tr>
        <tr>
            <td width="200">Введите имя</td>
            <td align="left"><input required type="text" name="name" placeholder="Имя"></td>
        </tr>
        <tr>
            <td width="200">Введите фамилию</td>
            <td align="left"><input required type="text" name="surname" placeholder="Фамилия"></td>
        </tr>
        <tr>
            <td width="200">Введите номер телефона</td>
            <td align="left"><input required type="text" name="phoneNumber" placeholder="Номер телефона"></td>
        </tr>
        <tr>
            <td><input type="hidden" name="status" value="клиент"></td>
            <td><input type="hidden" name="commandName" value="save_user">
        </tr>
        <tr><td><input type="submit" value="Сохранить"></td></tr>
    </table>
</form>
</body>
</html>
