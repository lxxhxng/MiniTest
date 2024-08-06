<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exam View</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/exam.css">
</head>
<body>
<h1>Exam: ${exam.examName}</h1>
<form action="${pageContext.request.contextPath}/exam/view" method="post">
    <input type="hidden" name="action" value="submit">
    <input type="hidden" name="examNum" value="${exam.eno}">

    <h2>Questions:</h2>
    <c:forEach var="quiz" items="${quizzes}">
        <div class="question">
            <p><strong>Q${quiz.qno}:</strong> ${quiz.question}</p>
            <ul>
                <li><input type="radio" name="answers[${quiz.qno}]" value="1"> ${quiz.op1}</li>
                <li><input type="radio" name="answers[${quiz.qno}]" value="2"> ${quiz.op2}</li>
                <li><input type="radio" name="answers[${quiz.qno}]" value="3"> ${quiz.op3}</li>
                <li><input type="radio" name="answers[${quiz.qno}]" value="4"> ${quiz.op4}</li>
                <c:if test="${not empty quiz.op5}">
                    <li><input type="radio" name="answers[${quiz.qno}]" value="5"> ${quiz.op5}</li>
                </c:if>
            </ul>
        </div>
    </c:forEach>

    <button type="submit">Submit</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/student/examList">Back to Exam List</a>
</body>
</html>
