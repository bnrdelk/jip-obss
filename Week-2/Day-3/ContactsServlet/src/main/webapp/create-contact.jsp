<%@ page import="com.example.contactsservlet.Contact" %><%--
  Created by IntelliJ IDEA.
  User: beyzanurdeliktas
  Date: 31.07.2024
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Create Contact</h1>
<form action="create-contact" method="post">
    Name: <input type="text" name="name" required>
    Telephone Number: <input type="text" name="gsm" required>
    <input type="submit" value="Submit">
</form>

</body>
</html>
