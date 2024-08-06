<%@ page import="org.example.miniproject_5.vo.ExamVO" %>
<html>
<head>
    <title>Take Exam</title>
</head>
<body>
<h1>Take Exam</h1>

<!-- Display exam details here -->
<!-- Example: -->
<form action="/student/submitExam" method="post">
    <input type="hidden" name="examId" value="${examDetails.examId}" />
    <!-- Add exam questions and options here -->
    <input type="submit" value="Submit Exam" />
</form>
</body>
</html>
