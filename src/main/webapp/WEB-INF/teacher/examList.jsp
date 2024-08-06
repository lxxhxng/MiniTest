<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-08-05
  Time: 오후 2:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Exam List</title>
</head>
<body>

<h1>Exam List</h1>

<form action="examList" method="post">

    <c:forEach var="exam" items="${examList}">
        <ul>
            <li><a href="/examResult?eno=${exam.eno}">${exam.eno}</a></li>
            <li>${exam.startTime}</li>
            <li>${exam.endTime}</li>
            <li>${exam.tno}</li>
            <li>${exam.examName}</li>
        </ul>
    </c:forEach>

    <button type="submit" class="btn btn-primary">REGISTER</button>

</form>

</body>
</html>
