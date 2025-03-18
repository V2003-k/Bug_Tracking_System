<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Close Bug</title>
</head>
<body>
    <h2>Close a Bug</h2>
    <form action="CloseBugServlet" method="post">
        Bug ID: <input type="text" name="bug_id" required><br>
        <input type="submit" value="Close Bug">
    </form>
</body>
</html>
