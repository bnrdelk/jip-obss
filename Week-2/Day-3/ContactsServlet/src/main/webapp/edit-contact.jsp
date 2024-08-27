<%@ page import="com.example.contactsservlet.Contact" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: beyzanurdeliktas
  Date: 31.07.2024
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Contact </title>
</head>
<body>
<h1>Edit Contact </h1>
<%
    Contact contact = (Contact) request.getAttribute("contact");
    if (contact != null) {
%>
<form action="edit-contact" method="post">
    <input type="hidden" name="id" value="<%= contact.getId() %>">
    Name: <input type="text" name="name" value="<%= contact.getName() %>" required>
    Telephone Number: <input type="text" name="gsm" value="<%= contact.getTelNumber() %>" required>
    <input type="submit" value="Update">
</form>
<%
} else {
%>
<p>Contact not found.</p>
<%
    }
%>

</body>
</html>
