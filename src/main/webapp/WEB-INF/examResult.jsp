<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-08-06
  Time: 오전 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Exam Results</title>
</head>
<body>

<h1>Exam Results</h1>

<!-- 오류 메시지가 있는 경우 표시 -->
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<!-- 선택된 시험 번호와 결과 표시 -->
<c:if test="${not empty examNo}">
    <p>Exam Number: ${examNo}</p>
    <table border="1">
        <thead>
        <tr>
            <th>Student Number (sno)</th>
            <th>Score</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="result" items="${resultList}">
            <tr>
                <td>${result.sno}</td>
                <td>${result.score}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

</body>
</html>
