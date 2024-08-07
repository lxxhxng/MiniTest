<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Answer Sheet</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            width: 80%;
            margin: 0 auto;
        }
        .title {
            font-size: 24px;
            margin-top: 20px;
            margin-bottom: 20px;
        }
        .answer-list {
            list-style-type: none;
            padding: 0;
        }
        .answer-list li {
            margin-bottom: 10px;
        }
        .answer-list a {
            text-decoration: none;
            color: #007BFF;
        }
        .answer-list input {
            border: 1px solid #ccc;
            padding: 5px;
            font-size: 16px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="title">Answer Sheet</div>
    <ul class="answer-list">
        <c:forEach items="${answerMap}" var="entry">
            <li>
                <a href="javascript:void(0);" onclick="move('${entry.key}')">${entry.key}번</a>
                <input type="text" readonly value="${entry.value}" />
            </li>
        </c:forEach>
    </ul>
</div>

<script>
    function move(quizNum) {
        // Assuming 'parent' is an iframe or parent window
        if (parent && typeof parent.move === 'function') {
            parent.move(quizNum);
        } else {
            console.error('Parent window or move function not found.');
        }
    }
</script>

</body>
</html>
