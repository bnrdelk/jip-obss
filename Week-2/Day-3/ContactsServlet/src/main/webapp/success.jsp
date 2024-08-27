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
<h1>SUCCESSFULLY IMPLEMENTED</h1>

<div>
    <%
        String message = (String) request.getAttribute("contactsList");
        if (message != null) {
    %>  <p>Name: <%= ((Contact) request.getAttribute("contacts")).getName() %></p>
    <p>Telephone Number: <%= ((Contact) request.getAttribute("contacts")).getTelNumber() %></p>

    <% } else {     %>
    <p>Contact has been deleted / not found.</p>
    <% }
    %>
    <br>
    <a href="search-contact.jsp">Back to Search</a>
    <a href="create-contact.jsp">Back to Create</a>
</div>
</body>
</html>
