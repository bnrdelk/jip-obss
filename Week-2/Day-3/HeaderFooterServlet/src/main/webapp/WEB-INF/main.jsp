<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>JSP - Hello Servlet</title>
</head>
<body>
<%@ include file="header.jsp" %> <!-- STATIC -->
<main>
    <p>MAIN</p>
</main>
<jsp:include page="footer.jsp" /> <!-- DYNAMIC -->
</body>
</html>
