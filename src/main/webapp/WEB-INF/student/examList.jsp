<%@ page import="java.util.List" %>
<%@ page import="org.example.miniproject_5.vo.StudentExamVO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exam List</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 800px;
            margin-top: 50px;
        }
        h1, h2 {
            color: #333;
        }
        .btn-custom {
            background-color: #f3e8c2;
            color: #333;
        }
        .btn-custom:hover {
            background-color: #f6e5ae;
            color: #333;
        }
        .form-group label {
            font-weight: bold;
        }
        .error-message {
            color: red;
            margin-top: 20px;
        }
        .exam-item {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 10px;
            background-color: #fff;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center mb-4">MINI TEST</h1>

    <!-- 에러 메시지가 있는 경우 표시 -->
    <c:if test="${not empty message}">
        <div class="error-message">
            <strong>${message}</strong>
        </div>
    </c:if>

    <h2>Not Attended Exams</h2>
    <form action="/student/examList" method="post">
        <div class="form-group">
            <label for="examId">Enter Exam ID to attend::</label>
            <input type="text" id="examId" name="examId" class="form-control" required />
        </div>
        <input type="hidden" name="action" value="takeExam" />
        <button type="submit" class="btn btn-custom btn-block">Attend Exam</button>
    </form>
    <br>
    <br>
    <br>
    <h2>Attended Exams</h2>
    <ul class="list-unstyled">
        <c:forEach var="exam" items="${attendedExams}">
            <li class="exam-item">
                <div>
                    <strong>${exam.examName}</strong> (Start: ${exam.startTime}, End: ${exam.endTime})
                </div>
                <a href="${pageContext.request.contextPath}/exam/result?examNum=${exam.eno}" class="btn btn-custom ml-2">View Results</a>
            </li>
        </c:forEach>
    </ul>

</div>

<!-- Bootstrap JS 및 종속성 -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
