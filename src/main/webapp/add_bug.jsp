<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Bug</title>
</head>
<body>
    <h2>Report a New Bug</h2>
    <form action="AddBugServlet" method="post">
        Title: <input type="text" name="title" required><br>
        Description: <textarea name="description" required></textarea><br>
        Severity: 
        <select name="severity">
            <option value="Low">Low</option>
            <option value="Medium">Medium</option>
            <option value="High">High</option>
        </select><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
