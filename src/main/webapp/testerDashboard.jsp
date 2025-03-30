<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tester Dashboard</title>
</head>
<body>
    <h2>Welcome, Tester</h2>
    <a href="LogoutServlet">Logout</a>
    <h3>Report a New Bug</h3>
    <form action="AddBugServlet" method="post">
        Title: <input type="text" name="title" required><br>
        Description: <textarea name="description" required></textarea><br>
        Priority: <select name="priority">
            <option value="Low">Low</option>
            <option value="Medium">Medium</option>
            <option value="High">High</option>
        </select>
        <br>
        <input type="submit" value="Report Bug">
    </form>
</body>
</html>