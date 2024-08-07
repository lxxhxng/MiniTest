<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h1>MINI TEST</h1>
        <form action="examList" method="post">
            <button type="submit" class="btn btn-register">REGISTER</button>
        </form>
        <form action="/tlogout" method="post">
            <button type="submit" class="btn btn-register">LOG OUT</button>
        </form>
    </div>

    <h2 class="text-center mb-4">Ongoing Exams</h2>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Exam Number</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Teacher Number</th>
            <th>Exam Name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="exam" items="${ongoingExams}">
            <tr>
                <td>${exam.eno}</td>
                <td>${exam.startTime}</td>
                <td>${exam.endTime}</td>
                <td>${exam.tno}</td>
                <td>${exam.examName}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2 class="text-center mb-4">Finished Exams</h2>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Exam Number</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Teacher Number</th>
            <th>Exam Name</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="exam" items="${finishedExams}">
            <tr>
                <td>${exam.eno}</td>
                <td>${exam.startTime}</td>
                <td>${exam.endTime}</td>
                <td>${exam.tno}</td>
                <td>${exam.examName}</td>
                <td>
                    <form action="/examResult" method="get" style="display:inline;">
                        <input type="hidden" name="eno" value="${exam.eno}" />
                        <button type="submit" class="action-button">View Results</button>
                    </form>
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
