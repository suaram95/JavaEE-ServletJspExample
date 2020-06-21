<%@ page import="model.User" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("users");
    List<User> users = (List<User>) request.getAttribute("users");
    if (user != null) {
%>

Welcome!! <%=user.getName()%> <%=user.getSurname()%>. You have successfully logged in<br>
<a href="/logout">Logout</a>
<%}%>
<br><br>
All Users:
<table border="1">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Surname</td>
        <td>Email</td>
        <td>delete</td>
    </tr>
    <% for (User user1 : users) {%>
    <tr>
        <td><%=user1.getId()%>
        </td>
        <td><%=user1.getName()%>
        </td>
        <td><%=user1.getSurname()%>
        </td>
        <td><%=user1.getEmail()%>
        </td>
        <td><a href="/removeUser?id=<%=user1.getId()%>">delete</a></td>
    </tr>
    <%} %>
</table>
</body>
</html>
