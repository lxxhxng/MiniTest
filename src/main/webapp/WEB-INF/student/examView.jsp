<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exam View</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 1200px;
            margin-top: 30px;
        }
        .exam-title {
            margin-bottom: 30px;
        }
        .exam-content {
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        .question {
            margin-bottom: 20px;
            padding: 10px;
            border-bottom: 1px solid #e1e1e1;
        }
        .question p {
            font-weight: bold;
        }
        .submit-button, .action-button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .submit-button:hover, .action-button:hover {
            background-color: #218838;
        }
        .answer-sheet {
            position: sticky;
            top: 0;
            height: 100vh;
            background-color: #ffffff;
            padding: 20px;
            border-left: 1px solid #e1e1e1;
            box-shadow: 4px 0 6px rgba(0,0,0,0.1);
        }
        .answer-sheet iframe {
            width: 100%;
            height: 100%;
            border: none;
        }
        .back-link {
            margin-top: 20px;
            display: inline-block;
            font-size: 16px;
        }
        .back-link a {
            text-decoration: none;
            color: #007bff;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="exam-title text-center">
        <h1>${exam.examName}</h1>
    </div>

    <div class="row">
        <div class="col-md-8">
            <div class="exam-content">
                <c:forEach var="quiz" items="${quizzes}">
                    <div class="question">
                        <form method="post" action="${pageContext.request.contextPath}/check/answer" target="answerSheet">
                            <input type="hidden" name="examno" value="${exam.eno}">
                            <input type="hidden" name="qno" value="${quiz.qno}">

                            <p><strong>Q${quiz.qno}:</strong> ${quiz.question}</p>
                            <ul class="list-unstyled">
                                <li><input type="radio" name="answer" value="1"> ${quiz.op1}</li>
                                <li><input type="radio" name="answer" value="2"> ${quiz.op2}</li>
                                <li><input type="radio" name="answer" value="3"> ${quiz.op3}</li>
                                <li><input type="radio" name="answer" value="4"> ${quiz.op4}</li>
                                <c:if test="${not empty quiz.op5}">
                                    <li><input type="radio" name="answer" value="5"> ${quiz.op5}</li>
                                </c:if>
                            </ul>
                            <button type="submit" class="btn submit-button">Submit</button>
                        </form>
                    </div>
                </c:forEach>

                <!-- 전체 시험 제출 버튼 추가 -->
                <form method="post" action="${pageContext.request.contextPath}/exam/submit" id="submitExamForm">
                    <input type="hidden" name="examNum" value="${exam.eno}">
                    <button type="submit" class="btn submit-button">Submit All</button>
                </form>

                <div class="back-link">
                    <a href="${pageContext.request.contextPath}/student/examList">Back to Exam List</a>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="answer-sheet">
                <iframe name="answerSheet" src="${pageContext.request.contextPath}/exam/answerSheet?examNum=${exam.eno}"></iframe>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
