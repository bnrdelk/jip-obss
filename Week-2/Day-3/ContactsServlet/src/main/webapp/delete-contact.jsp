<%@ page import="com.example.contactsservlet.Contact" %><%--
  Created by IntelliJ IDEA.
  User: beyzanurdeliktas
  Date: 31.07.2024
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Contact</title>
</head>
<body>
<h2>Are you sure?</h2>
<% Contact contact = (Contact) request.getAttribute("contact"); %>
<form action="delete-contact" method="post">
    <input type="hidden" name="id" value="<%= contact.getId() %>">
    <p>Name: <%= contact.getName() %></p>
    <p>Telephone Number: <%= contact.getTelNumber() %> will be deleted.</p>
    <input type="submit" value="Delete">
</form>
</body>
</html>
