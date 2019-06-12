<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
    <jsp:useBean id="fullListOfMeals" scope="request" type="java.util.List"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="users">Users</a></h3>
<h3><a href="meals">Meals</a></h3>
<hr>
<h2>Meals</h2>
<table border="2" width="100%" cellpadding="5">
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Действия</th>
    </tr>
    <c:forEach items="${fullListOfMeals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: ${(meal.excess==true? 'red' : 'green')}">
            <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                <fmt:formatDate value="${parsedDateTime}" pattern="yyyy.MM.dd HH:mm"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=delete&uuid=${meal.uuid}">Delete</a><br><a
                    href="meals?action=edit&uuid=${meal.uuid}">Edit</a></td>
        </tr>
    </c:forEach>
</table>
<hr>
<section>
    <form action="meals" method="post">
        <input type="datetime-local" name="dateTime" placeholder="Дата/Время">
        <input type="text" name="description" placeholder="Название еды">
        <input type="number" name="calories" placeholder="Количество калорий">
        <input type="submit" value="Добавить">
    </form>
</section>
</body>
</html>