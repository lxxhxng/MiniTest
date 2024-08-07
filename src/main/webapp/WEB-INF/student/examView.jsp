<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exam View</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/exam.css">
    <style>
        .container {
            display: flex;
        }
        .exam-content {
            flex: 1;
            padding: 20px;
        }
        .answer-sheet {
            width: 300px; /* Adjust width as needed */
            border-left: 1px solid black;
            padding: 10px;
            background-color: #f9f9f9;
            height: 100vh; /* Full viewport height */
            overflow: auto;
        }
        iframe {
            width: 100%;
            height: 100%;
            border: none;
        }
        .question {
            margin-bottom: 20px;
        }
        .submit-button {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .submit-button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Exam: ${exam.examName}</h1>

<div class="container">
    <div class="exam-content">
        <c:forEach var="quiz" items="${quizzes}">
            <div class="question">
                <form method="post" action="${pageContext.request.contextPath}/check/answer" target="answerSheet">
                    <input type="hidden" name="examno" value="${exam.eno}">
                    <input type="hidden" name="qno" value="${quiz.qno}">

                    <p><strong>Q${quiz.qno}:</strong> ${quiz.question}</p>
                    <ul>
                        <li><input type="radio" name="answer" value="1"> ${quiz.op1}</li>
                        <li><input type="radio" name="answer" value="2"> ${quiz.op2}</li>
                        <li><input type="radio" name="answer" value="3"> ${quiz.op3}</li>
                        <li><input type="radio" name="answer" value="4"> ${quiz.op4}</li>
                        <c:if test="${not empty quiz.op5}">
                            <li><input type="radio" name="answer" value="5"> ${quiz.op5}</li>
                        </c:if>
                    </ul>
                    <button type="submit">Submit</button>
                </form>
            </div>
        </c:forEach>

        <!-- 전체 시험 제출 버튼 추가 -->
        <form method="post" action="${pageContext.request.contextPath}/exam/submit" id="submitExamForm">
            <input type="hidden" name="examNum" value="${exam.eno}">
            <button type="submit" class="submit-button">Submit All</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/student/examList">Back to Exam List</a>
    </div>

    <div class="answer-sheet">
        <iframe name="answerSheet" src="${pageContext.request.contextPath}/exam/answerSheet?examNum=${exam.eno}"></iframe>
    </div>
</div>

</body>
</html>
