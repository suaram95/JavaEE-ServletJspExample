
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%if (request.getAttribute("message")!=null) {%>
<p style="color: red"><%= request.getAttribute("message")%></p>
<%}%>
<form action="/login" method="post">
    Email: <input type="text" name="email"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" value="Login" name="Login">
</form>
</body>
</html>
