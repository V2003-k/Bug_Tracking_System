<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="javax.servlet.http.*, javax.servlet.*" %>
<%
    HttpSession session2 = request.getSession(false);
    if (session2 == null || session2.getAttribute("role") == null || 
        !session2.getAttribute("role").equals("Developer")) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Developer Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= session2.getAttribute("name") %> (Developer)</h2>
    <a href="logout.jsp">Logout</a>
    <h3>Developer Features</h3>
    <ul>
        <li><a href="viewAssignedBugs.jsp">View Assigned Bugs</a></li>
        <li><a href="updateBugStatus.jsp">Update Bug Status</a></li>
    </ul>
</body>
</html>
