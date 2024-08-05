<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>강사 로그인</title>
</head>
<body>
<h1>강사 로그인</h1>

<form action="/teacher/login" method="post">
    <label for="tid">ID:</label>
    <input type="text" id="tid" name="tid" required><br><br>

    <label for="tpw">Password:</label>
    <input type="password" id="tpw" name="tpw" required><br><br>

    <input type="submit" value="Login">
</form>

<% if (request.getParameter("error") != null) { %>
<p style="color: red;">아이디나 비밀번호가 유요하지 않습니다.</p>
<% } %>
</body>
</html>
