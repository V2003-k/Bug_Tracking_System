<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Verify Bug</title>
</head>
<body>
    <h2>Verify Bug Status</h2>
    <form action="VerifyBugServlet" method="post">
        Bug ID: <input type="text" name="bug_id" required><br>
        <input type="submit" value="Verify">
    </form>
</body>
</html>
