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
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .container {
            max-width: 1200px;
            margin-top: 50px;
        }

        .table thead th {
            background-color: #f6e5ae;
            color: white;
        }

        .table tbody tr:hover {
            background-color: #f1f1f1;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .header h1 {
            margin: 0;
        }

        .btn-register {
            background-color: #f3e8c2; /* Custom color for REGISTER button */
            color: white;
        }

        .btn-register:hover {
            background-color: #f6e5ae; /* Darker color on hover */
            color: white;
        }

        .action-button {
            border: none;
            background: none;
            color: #28a745; /* Custom color for View Results button */
            cursor: pointer;
        }

        .action-button:hover {
            text-decoration: underline;
            color: #218838; /* Darker color on hover */
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Student Result</h1>
        <form action="/student/examList" method="get">
            <button type="submit" class="btn btn-register">목록</button>
        </form>
    </div>

    <table class="table table-bordered table-striped">
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
</div>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
