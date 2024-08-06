<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MINI TEST(강사용)</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            height: 100vh; /* Viewport height: 100% */
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            max-width: 400px;
        }
        .btn-custom {
            background-color: #f3e8c2;
            color: white;
            font-size: 1.2em;
        }
        .btn-custom:hover {
            background-color: #f6e5ae;
        }
        .form-group label {
            font-weight: bold;
        }
        .error-message {
            color: red;
            margin-top: 20px;
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>MINI TEST(강사용)</h1>
    <form action="/teacher/login" method="post">
        <div class="form-group">
            <label for="tid">ID:</label>
            <input type="text" id="tid" name="tid" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="tpw">Password:</label>
            <input type="password" id="tpw" name="tpw" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-custom btn-block">Login</button>
    </form>

    <% if (request.getParameter("error") != null) { %>
    <p class="error-message">아이디나 비밀번호가 유요하지 않습니다.</p>
    <% } %>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
