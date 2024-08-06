<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>시험 목록</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            padding: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        .header h1 {
            margin: 0;
            font-size: 2em;
            color: #343a40;
        }
        .exam-card {
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .exam-card:hover {
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }
        .btn-primary {
            background-color: #f3e8c2;
            color: white;
            border: none;
        }
        .btn-primary:hover {
            background-color: #f6e5ae;
            color: black;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h1>MINI TEST</h1>
        <form action="examList" method="post">
            <button type="submit" class="btn btn-primary">시험 등록</button>
        </form>
    </div>

    <c:forEach var="exam" items="${examList}">
        <div class="exam-card">
            <h4>시험 이름: ${exam.examName}</h4>
            <p>시험 번호: <a href="/examResult?eno=${exam.eno}">${exam.eno}</a></p>
            <p>시작 시간: ${exam.startTime}</p>
            <p>종료 시간: ${exam.endTime}</p>
            <p>강사 번호: ${exam.tno}</p>
        </div>
    </c:forEach>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
