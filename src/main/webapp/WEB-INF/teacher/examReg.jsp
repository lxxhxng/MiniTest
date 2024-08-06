
<html>
<head>
    <title>Upload Exam Data</title>
</head>
<body>

<h2>Upload Exam Data</h2>

<form action="/teacher/examReg" method="post" enctype="multipart/form-data">
    <label for="title">title:</label>
    <input type="text" id="title" name="title">
    <br>
    <label for="stime">Start Time:</label>
    <input type="datetime-local" id="stime" name="stime" required>
    <br>
    <label for="etime">End Time:</label>
    <input type="datetime-local" id="etime" name="etime" required>
    <br>
    <label for="file">Select Excel file:</label>
    <input type="file" id="file" name="examFile" accept=".xlsx, .xls" required>
    <br><br>
    <input type="submit" value="Upload">
</form>

</body>
</html>

</body>
</html>