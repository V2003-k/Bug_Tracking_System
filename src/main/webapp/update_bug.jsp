<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Bug</title>
</head>
<body>
    <h2>Update Bug</h2>
    <form action="UpdateBugServlet" method="post">
        Bug ID: <input type="text" name="bug_id" required><br>
        New Status: 
        <select name="status">
            <option value="Open">Open</option>
            <option value="In Progress">In Progress</option>
            <option value="Resolved">Resolved</option>
        </select><br>
        <input type="submit" value="Update">
    </form>
</body>
</html>
