<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 선택</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            height: 100vh; /* Viewport height: 100% */
            text-align: center;
        }
        .btn-custom {
            background-color: #f3e8c2;
            color: white;
            width: 250px;
            height: 300px;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0;
            font-size: 2em;
        }
        .btn-custom:hover {
            background-color: #f6e5ae;
        }
        .title {
            font-size: 2.5em;
            margin-bottom: 40px;
            color: #333;
        }
    </style>
</head>
<body>
<div class="container d-flex flex-column justify-content-center align-items-center">
    <h1 class="title">MINI TEST</h1>
    <div class="d-flex justify-content-center">
        <form action="teacher/login" method="get" class="mx-5">
            <button type="submit" class="btn btn-custom">
                강사
            </button>
        </form>
        <form action="student/login" method="get" class="mx-5">
            <button type="submit" class="btn btn-custom">
                학생
            </button>
        </form>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
