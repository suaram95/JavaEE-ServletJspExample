<%@ page import="model.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<% List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessons");%>
<form action="/register" method="post">

    Name: <input type="text" name="name"><br>
    Surname: <input type="text" name="surname"><br>
    Email: <input type="text" name="email"><br>
    Password: <input type="password" name="password"><br>
    Gender: <input type="radio" value="MALE" name="gender"> MALE
    <input type="radio" value="FEMALE" name="gender"> FEMALE<br>
    Please select lesson
    <select name="lessonId">
        <%for (Lesson lesson : lessons) {%>
        <option value="<%=lesson.getId()%>"><%=lesson.getName()%>
        </option>
        <%}%>
    </select>
    <br>
    <input type="submit" value="Register">

</form>
</body>
</html>
