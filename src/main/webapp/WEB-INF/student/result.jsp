<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exam Result</title>
</head>
<body>
<h1>Exam Result</h1>
<p>Exam Number: ${examNum}</p>
<p>Score: ${score}</p>

<h2>Your Answers</h2>
<table border="1">
    <thead>
    <tr>
        <th>Question Number</th>
        <th>Selected Answer</th>
        <th>Correct Answer</th>
        <th>Correct</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="answer" items="${answers}">
        <tr>
            <td>${answer.qno}</td>
            <td>${answer.checkedNum}</td>
            <td>
                <c:choose>
                    <c:when test="${answer.correct}">
                        Correct
                    </c:when>
                    <c:otherwise>
                        Incorrect
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="${pageContext.request.contextPath}/student/examList">Back to Exam List</a>
</body>
</html>
