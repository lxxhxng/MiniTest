<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Result Detail</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            margin-top: 20px;
        }
        .container {
            max-width: 1000px;
        }
        .table thead th {
            background-color: #f6e5ae;
            color: white;
        }
        .table tbody tr:hover {
            background-color: #f1f1f1;
        }
        .correct-answer {
            color: green;
            font-weight: bold;
        }
        .incorrect-answer {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="text-center mb-4">Result Details</h1>

    <!-- Results Table -->
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>번호</th>
            <th>문제</th>
            <th>보기1</th>
            <th>보기2</th>
            <th>보기3</th>
            <th>보기4</th>
            <th>보기5</th>
            <th>정답</th>
            <th>선택</th>
            <th>정답 유무</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="detail" items="${resultDetail}">
            <tr>
                <td>${detail.no}</td>
                <td>${detail.question}</td>
                <td>${detail.op1}</td>
                <td>${detail.op2}</td>
                <td>${detail.op3}</td>
                <td>${detail.op4}</td>
                <td>${detail.op5}</td>
                <td class="correct-answer">${detail.answer}</td>
                <td>${detail.checked}</td>
                <td>
                    <span class="<c:if test='${detail.correct}'>correct-answer</c:if><c:if test='${!detail.correct}'>incorrect-answer</c:if>">
                        <c:if test="${detail.correct}">정답</c:if>
                        <c:if test="${!detail.correct}">오답</c:if>
                    </span>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
