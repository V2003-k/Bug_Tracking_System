<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
    <h2>Signup Form</h2>
    <form action="RegisterServlet" method="post">
        Name: <input type="text" name="name" required><br><br>
        Email: <input type="email" name="email" required><br><br>
        Password: <input type="password" name="password" required><br><br>
        Role: 
        <select name="role">
            <option value="Admin">Admin</option>
            <option value="Developer">Developer</option>
            <option value="Manager">Manager</option>
        </select>
        <br><br>
        <input type="submit" value="Register">
    </form>
</body>
</html>
