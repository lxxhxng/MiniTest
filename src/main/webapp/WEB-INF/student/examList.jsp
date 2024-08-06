<%@ page import="java.util.List" %>
<%@ page import="org.example.miniproject_5.vo.StudentExamVO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Exam List</title>
</head>
<body>
<h1>Exam List</h1>

<h2>Attended Exams</h2>
<ul>
    <c:forEach var="exam" items="${attendedExams}">
        <li>
                ${exam.examName} (Start: ${exam.startTime}, End: ${exam.endTime})
            <form action="/student/examList" method="post">
                <input type="hidden" name="action" value="viewResult" />
                <input type="hidden" name="examId" value="${exam.eno}" />
                <input type="submit" value="View Results" />
            </form>
        </li>
    </c:forEach>
</ul>

<h2>Not Attended Exams</h2>
<ul>
    <c:forEach var="exam" items="${notAttendedExams}">
        <li>
                ${exam.examName} (Start: ${exam.startTime}, End: ${exam.endTime})
            <form action="/student/examList" method="post">
                <input type="hidden" name="action" value="takeExam" />
                <input type="hidden" name="examId" value="${exam.eno}" />
                <input type="submit" value="Attend Exam" />
            </form>
        </li>
    </c:forEach>
</ul>
</body>
</html>
