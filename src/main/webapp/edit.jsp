<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="users">Users</a></h3>
<h3><a href="meals">Meals</a></h3>
<hr>
<h2>Users</h2>
<form action="meals" method="post">
    <input type="hidden" name="uuid" value="${meal.uuid}">
    <dl>
        <dt>Дата/Время</dt>
        <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"></dd>
        <dt>Описание</dt>
        <dd><input type="text" name="description" value="${meal.description}"></dd>
        <dt>Калории</dt>
        <dd><input type="number" name="calories" value="${meal.calories}"></dd>
    </dl>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()">Отменить</button>
</form>
</body>
</html>