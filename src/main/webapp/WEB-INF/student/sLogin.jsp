<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- /WEB-INF/student/sLogin.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>Student Login</title>
</head>
<body>
<h2>Student Login</h2>
<form action="/student/login" method="post">
    <label for="sid">ID:</label>
    <input type="text" id="sid" name="sid" required><br>
    <label for="spw">Password:</label>
    <input type="password" id="spw" name="spw" required><br>
    <input type="submit" value="Login">
</form>
<c:if test="${param.error == 'invalid'}">
    <p style="color: red;">Invalid ID or Password</p>
</c:if>
</body>
</html>
