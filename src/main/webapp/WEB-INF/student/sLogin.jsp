<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- /WEB-INF/student/sLogin.jsp -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            max-width: 400px;
            width: 100%;
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
        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>MINI TEST(학생용)</h2>

    <form action="/student/login" method="post">
        <div class="form-group">
            <label for="sid">ID:</label>
            <input type="text" id="sid" name="sid" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="spw">Password:</label>
            <input type="password" id="spw" name="spw" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-custom btn-block">Login</button>

        <% if (request.getParameter("error") != null) { %>
        <p class="error-message">아이디나 비밀번호가 유요하지 않습니다.</p>
        <% } %>
    </form>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
