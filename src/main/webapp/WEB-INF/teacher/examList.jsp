<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Exam List</title>
    <style>
        .action-button {
            margin-left: 10px;
        }
    </style>
</head>
<body>

<h1>Exam List</h1>

<table border="1">
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
    <c:forEach var="exam" items="${examList}">
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

<!-- 등록 버튼을 포함하는 폼을 테이블 외부에 배치 -->
<form action="examList" method="post" style="margin-top: 10px;">
    <button type="submit" class="btn btn-primary">REGISTER</button>
</form>

</body>
</html>
