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
    <title>Search Contact</title>
</head>
<body>
<h1>Search Contact</h1>
<form action="search-contact" method="get">
    Name: <input type="text" name="name" required>
    <input type="submit" value="Search">
</form>
<%
    String message = (String) request.getAttribute("contactsList");
    if (message != null) {
%>  <p>Name: <%= ((Contact) request.getAttribute("contacts")).getName() %></p>
            <p>Telephone Number: <%= ((Contact) request.getAttribute("contacts")).getTelNumber() %></p>

<%}
%>

</body>
</html>
