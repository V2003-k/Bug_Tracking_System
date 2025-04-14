<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bug List - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h4>Bug List</h4>
                <a href="bug?action=new" class="btn btn-primary">Report New Bug</a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Project</th>
                                <th>Reported By</th>
                                <th>Assigned To</th>
                                <th>Status</th>
                                <th>Priority</th>
                                <th>Created</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="bug" items="${bugs}">
                                <tr>
                                    <td>${bug.bugId}</td>
                                    <td>${bug.title}</td>
                                    <td>${bug.projectName}</td>
                                    <td>${bug.reportedByName}</td>
                                    <td>${bug.assignedToName}</td>
                                    <td>
                                        <span class="badge ${bug.status == 'OPEN' ? 'badge-danger' : bug.status == 'IN_PROGRESS' ? 'badge-warning' : bug.status == 'FIXED' ? 'badge-success' : 'badge-primary'}">
                                            ${bug.status}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge ${bug.priority == 'LOW' ? 'badge-info' : bug.priority == 'MEDIUM' ? 'badge-warning' : bug.priority == 'HIGH' ? 'badge-danger' : 'badge-dark'}">
                                            ${bug.priority}
                                        </span>
                                    </td>
                                    <td>${bug.createdDate}</td>
                                    <td>
                                        <a href="bug?action=view&id=${bug.bugId}" class="btn btn-info btn-sm">View</a>
                                        <a href="bug?action=edit&id=${bug.bugId}" class="btn btn-warning btn-sm">Edit</a>
                                        <a href="bug?action=delete&id=${bug.bugId}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this bug?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>