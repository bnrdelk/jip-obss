<%--
  Created by IntelliJ IDEA.
  User: beyzanurdeliktas
  Date: 1.08.2024
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="name">Username</label><br>
    <input type="text" id="name" name="name"><br>

    <label for="password">Password</label><br>
    <input type="password" id="password" name="password"><br><br>

    <input type="submit" value="Submit">
</form>
</body>
</html>
