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
    <title>Found Contact List</title>
</head>
<body>
<h1>Found Contact List</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Telephone Number</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <%
        List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
        for (Contact contact : contacts) {
    %>
    <tr>
        <td><%= contact.getId() %></td>
        <td><%= contact.getName() %></td>
        <td><%= contact.getTelNumber() %></td>
        <td><a href="edit-contact?id=<%= contact.getId() %>">Edit</a></td>
        <td><a href="delete-contact?id=<%= contact.getId() %>">Delete</a></td>
    </tr>
    <% } %>
</table>

</body>
</html>
