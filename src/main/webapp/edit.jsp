<%--
  Created by IntelliJ IDEA.
  User: cmak
  Date: 07.02.2021
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Meal</title>
</head>
<body>

<form method="POST" action='meals' name="frmAddMeal">
    Date : <input
        type="text" name="dateTime"
        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
        value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />
    <input type="hidden" name="id"
           value="<c:out value="${meal.id}" />" /> <br />
    <input
        type="submit" value="Submit" />
    <button type="button" onclick="window.history.back();">Cancel</button>
</form>
</body>
</html>
