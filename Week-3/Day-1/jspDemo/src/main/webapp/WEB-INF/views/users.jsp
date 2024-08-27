<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>Welcome, <%= request.getAttribute("username") %>!</h1>

<h2>List of Items:</h2>
<ul>
    <%
        List<Integer> listOfItems = (List<Integer>) request.getAttribute("listOfItems");
        if (listOfItems != null) {
            for (Integer item : listOfItems) {
    %>
    <li><%= item %></li>
    <%
            }
        }
    %>
</ul>
</body>
</html>
