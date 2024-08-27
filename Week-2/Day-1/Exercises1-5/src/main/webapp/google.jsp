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
    <h3>Where do you want to go?</h3>
    <a href="google.com">Google!</a>
    <a href="youtube.com">Youtube!</a>
    <a href="index.jsp">Go Back!</a>
</div>

<br/>
</body>
</html>