<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Exam Data</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
        }
        .form-group label {
            font-weight: bold;
        }
        .btn-custom {
            background-color: #f3e8c2;
            color: white;
        }
        .btn-custom:hover {
            background-color: #f6e5ae;
            color: white;
        }
        .form-control {
            border-radius: 0.25rem;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="text-center mb-4">UPLOAD</h1>

    <form action="/teacher/examReg" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" class="form-control" placeholder="시험 제목" required>
        </div>

        <div class="form-group">
            <label for="stime">Start Time:</label>
            <input type="datetime-local" id="stime" name="stime" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="etime">End Time:</label>
            <input type="datetime-local" id="etime" name="etime" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="file">Select Excel file:</label>
            <input type="file" id="file" name="examFile" class="form-control-file" accept=".xlsx, .xls" required>
        </div>
        <br>
        <button type="submit" class="btn btn-custom btn-block">Upload</button>
    </form>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.10/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
