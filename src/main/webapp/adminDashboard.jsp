<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="javax.servlet.http.*, javax.servlet.*" %>
<%
	HttpSession session1 = request.getSession(false);
    if (session1 == null || session1.getAttribute("role") == null || 
        !session1.getAttribute("role").equals("Admin")) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= session1.getAttribute("name") %> (Admin)</h2>
    <a href="logout.jsp">Logout</a>
    <h3>Admin Features</h3>
    <ul>
        <li><a href="manageUsers.jsp">Manage Users</a></li>
        <li><a href="manageProjects.jsp">Manage Projects</a></li>
        <li><a href="manageBugs.jsp">Manage Bugs</a></li>
    </ul>
</body>
</html>  
