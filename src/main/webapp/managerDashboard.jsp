<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="javax.servlet.http.*, javax.servlet.*" %>
<%
    HttpSession session3 = request.getSession(false);
    if (session3 == null || session3.getAttribute("role") == null || 
        !session3.getAttribute("role").equals("Manager")) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manager Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= session3.getAttribute("name") %> (Manager)</h2>
    <a href="logout.jsp">Logout</a>
    <h3>Manager Features</h3>
    <ul>
        <li><a href="viewAllBugs.jsp">View All Bugs</a></li>
        <li><a href="assignBugs.jsp">Assign Bugs to Developers</a></li>
    </ul>
</body>
</html>
