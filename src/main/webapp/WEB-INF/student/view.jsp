<%@ page import="java.util.List" %>
<%@ page import="org.example.miniproject_5.vo.QuizVO" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>시험 응시</title>
</head>
<body>
<h1>시험 문제</h1>
<form action="/check/answer" method="post">
    <c:forEach var="quiz" items="${quizList}">
        <div>
            <h3>문제 ${quiz.qno}</h3>
            <p>${quiz.question}</p>
            <input type="radio" name="answers[${quiz.qno}]" value="1" id="q${quiz.qno}_1" ${fn:contains(cookieAnswer, quiz.qno + ":1") ? 'checked' : ''}>
            <label for="q${quiz.qno}_1">${quiz.op1}</label><br>
            <input type="radio" name="answers[${quiz.qno}]" value="2" id="q${quiz.qno}_2" ${fn:contains(cookieAnswer, quiz.qno + ":2") ? 'checked' : ''}>
            <label for="q${quiz.qno}_2">${quiz.op2}</label><br>
            <input type="radio" name="answers[${quiz.qno}]" value="3" id="q${quiz.qno}_3" ${fn:contains(cookieAnswer, quiz.qno + ":3") ? 'checked' : ''}>
            <label for="q${quiz.qno}_3">${quiz.op3}</label><br>
            <input type="radio" name="answers[${quiz.qno}]" value="4" id="q${quiz.qno}_4" ${fn:contains(cookieAnswer, quiz.qno + ":4") ? 'checked' : ''}>
            <label for="q${quiz.qno}_4">${quiz.op4}</label><br>
            <input type="radio" name="answers[${quiz.qno}]" value="5" id="q${quiz.qno}_5" ${fn:contains(cookieAnswer, quiz.qno + ":5") ? 'checked' : ''}>
            <label for="q${quiz.qno}_5">${quiz.op5}</label><br>
        </div>
    </c:forEach>
    <button type="submit">제출</button>
</form>
</body>
</html>
