<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-08-06
  Time: 오후 5:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Student Exam Result</title>
</head>
<body>
<h1>Student Result</h1>

<table>
    <thead>
    <tr>
        <th>학생 번호(sno)</th>
        <th>문제 번호(qno)</th>
        <th>정답 여부(correct)</th>
        <th>체크된 번호(checked_num)</th>
        <th>표시된 번호(std)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="result" items="${results}">
        <tr>
            <td>${result.sno}</td>
            <td>${result.qno}</td>
            <td>${result.correct ? "정답" : "오답"}</td>
            <td>${result.checkedNum}</td>
            <td>${result.std}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
