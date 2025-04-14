<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Developer Dashboard - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <h2>Developer Dashboard</h2>
        <p>Welcome, ${user.fullName}!</p>
        
        <div class="row mt-4">
            <!-- Assigned Bugs -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-primary text-white">
                        <h5><i class="fas fa-tasks"></i> Bug Assignment Management</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="bug?action=assigned" class="list-group-item list-group-item-action">
                                <i class="fas fa-clipboard-list"></i> View Assigned Bugs
                            </a>
                            <a href="bug?action=inprogress" class="list-group-item list-group-item-action">
                                <i class="fas fa-spinner"></i> Bugs In Progress
                            </a>
                            <a href="bug?action=fixed" class="list-group-item list-group-item-action">
                                <i class="fas fa-check-circle"></i> Fixed Bugs
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Bug Details -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-info text-white">
                        <h5><i class="fas fa-info-circle"></i> Bug Details & Projects</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="bug?action=list" class="list-group-item list-group-item-action">
                                <i class="fas fa-bug"></i> View All Bugs
                            </a>
                            <a href="project?action=list" class="list-group-item list-group-item-action">
                                <i class="fas fa-project-diagram"></i> View Projects
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Quick Stats -->
        <div class="row mt-4">
            <div class="col-md-4 mb-4">
                <div class="card bg-primary text-white">
                    <div class="card-body text-center">
                        <h3>${assignedBugs}</h3>
                        <p>Assigned Bugs</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-4">
                <div class="card bg-warning text-dark">
                    <div class="card-body text-center">
                        <h3>${inProgressBugs}</h3>
                        <p>In Progress</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-4">
                <div class="card bg-success text-white">
                    <div class="card-body text-center">
                        <h3>${fixedBugs}</h3>
                        <p>Fixed Bugs</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Assigned Bugs List -->
        <div class="card mt-4">
            <div class="card-header bg-primary text-white">
                <h5><i class="fas fa-clipboard-list"></i> Recently Assigned Bugs</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Project</th>
                                <th>Priority</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="bug" items="${recentAssignedBugs}">
                                <tr>
                                    <td>${bug.bugId}</td>
                                    <td>${bug.title}</td>
                                    <td>${bug.projectName}</td>
                                    <td>
                                        <span class="badge ${bug.priority == 'LOW' ? 'badge-info' : bug.priority == 'MEDIUM' ? 'badge-warning' : bug.priority == 'HIGH' ? 'badge-danger' : 'badge-dark'}">
                                            ${bug.priority}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge ${bug.status == 'OPEN' ? 'badge-danger' : bug.status == 'IN_PROGRESS' ? 'badge-warning' : bug.status == 'FIXED' ? 'badge-success' : 'badge-primary'}">
                                            ${bug.status}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="bug?action=view&id=${bug.bugId}" class="btn btn-info btn-sm">View</a>
                                        <a href="bug?action=updateStatus&id=${bug.bugId}" class="btn btn-warning btn-sm">Update Status</a>
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