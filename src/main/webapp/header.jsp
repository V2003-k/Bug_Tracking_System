<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">Bug Tracking System</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/project?action=list">Projects</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/bug?action=list">Bugs</a>
                </li>
                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user?action=list">Users</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/report">Reports</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        ${sessionScope.user.fullName}
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/profile">My Profile</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/changePassword">Change Password</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="dashboard">Bug Tracking System</a>
    <button
      class="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarNav"
      aria-controls="navbarNav"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link" href="dashboard">Dashboard</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="project?action=list">Projects</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="bug?action=list">Bugs</a>
        </li>
        <c:if test="${user.role == 'Admin'}">
          <li class="nav-item">
            <a class="nav-link" href="user?action=list">Users</a>
          </li>
        </c:if>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item dropdown">
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="navbarDropdown"
            role="button"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            ${user.fullName}
          </a>
          <div
            class="dropdown-menu dropdown-menu-right"
            aria-labelledby="navbarDropdown"
          >
            <a class="dropdown-item" href="user?action=profile">Profile</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="logout">Logout</a>
          </div>
        </li>
      </ul>
    </div>
  </div>
>>>>>>> ccb8a1e (almost done)
</nav>
