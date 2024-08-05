<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Excel File</title>
</head>
<body>

<h2>Upload Excel File</h2>

<form action="/examreg" method="post" enctype="multipart/form-data">
    <label for="file">Select Excel file:</label>
    <input type="datetime-local" name="stime">
    <input type="datetime-local" name="etime">
    <input type="file" id="file" name="examFile" accept=".xlsx, .xls" required>
    <br><br>
    <input type="submit" value="Upload">
</form>

</body>
</html>
