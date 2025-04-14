<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Projects - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h4>Projects</h4>
                <a href="project?action=new" class="btn btn-primary">Add New Project</a>
            </div>
            <div class="card-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="project" items="${projects}">
                            <tr>
                                <td>${project.projectId}</td>
                                <td>${project.projectName}</td>
                                <td>${project.startDate}</td>
                                <td>${project.endDate}</td>
                                <td>
                                    <span class="badge ${project.status == 'ACTIVE' ? 'badge-success' : project.status == 'COMPLETED' ? 'badge-primary' : 'badge-warning'}">
                                        ${project.status}
                                    </span>
                                </td>
                                <td>
                                    <a href="project?action=view&id=${project.projectId}" class="btn btn-info btn-sm">View</a>
                                    <a href="project?action=edit&id=${project.projectId}" class="btn btn-warning btn-sm">Edit</a>
                                    <a href="project?action=delete&id=${project.projectId}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this project?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>