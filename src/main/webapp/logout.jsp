<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="javax.servlet.http.*, javax.servlet.*" %>
<%
    HttpSession sessionLogout = request.getSession(false);
    if (sessionLogout != null) {
        sessionLogout.invalidate();
    }
    response.sendRedirect("login.jsp");
%>
