<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Exam List</title>
</head>
<body>
<h1>Welcome, <c:out value="${student.sid}"/>!</h1>

<h2>Exams List</h2>

<!-- Display attended exams -->
<h3>Attended Exams</h3>
<c:if test="${not empty attendedExams}">
    <table border="1">
        <thead>
        <tr>
            <th>Exam Name</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Teacher Number</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="exam" items="${attendedExams}">
            <tr>
                <td><c:out value="${exam.ename}"/></td>
                <td><c:out value="${exam.stime}"/></td>
                <td><c:out value="${exam.etime}"/></td>
                <td><c:out value="${exam.tno}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty attendedExams}">
    <p>No attended exams found.</p>
</c:if>

<!-- Display not attended exams -->
<h3>Not Attended Exams</h3>
<c:if test="${not empty notAttendedExams}">
    <table border="1">
        <thead>
        <tr>
            <th>Exam Name</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Teacher Number</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="exam" items="${notAttendedExams}">
            <tr>
                <td><c:out value="${exam.ename}"/></td>
                <td><c:out value="${exam.stime}"/></td>
                <td><c:out value="${exam.etime}"/></td>
                <td><c:out value="${exam.tno}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty notAttendedExams}">
    <p>No not attended exams found.</p>
</c:if>
</body>
</html>
