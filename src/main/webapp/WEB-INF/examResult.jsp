<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exam Results</title>
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
        }
        .btn-detail {
            background-color: #f3e8c2;
            color: black;
            border: none;
            cursor: pointer;
        }
        .btn-detail:hover {
            background-color: #f6e5ae;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center mb-4">Exam Results</h1>

    <!-- 오류 메시지가 있는 경우 표시 -->
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <!-- 선택된 시험 번호와 결과 표시 -->
    <c:if test="${not empty examNo}">
        <p>Exam Number: ${examNo}</p>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Student Number (sno)</th>
                <th>Score</th>
                <th>Details</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="result" items="${resultList}">
                <tr>
                    <td>${result.sno}</td>
                    <td>${result.score}</td>
                    <td>
                        <form action="/examResult" method="post" style="display:inline;">
                            <input type="hidden" name="examNo" value="${examNo}" />
                            <input type="hidden" name="studentNo" value="${result.sno}" />
                            <button type="submit" class="btn btn-detail">View Details</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
