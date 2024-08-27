<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" session="false" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<%
    String name = request.getParameter("name");
    if(name == null){
        name = "Anonymus";
    }
%>
<div class="container">
    <h1>Welcome, <%= name %>!</h1>
    <h5>please enter your name & desired destination (index.jsp, google.jsp, error.jsp is valid): </h5>
    <form action="hello" method="get">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>
        <label for="destination">Destination:</label>
        <input type="text" id="destination" name="destination" required><br><br>
        <input type="submit" value="Submit">
    </form>
</div>

<br/>
</body>
</html>