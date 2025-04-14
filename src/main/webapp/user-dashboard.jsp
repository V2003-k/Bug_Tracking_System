<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Dashboard - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <h2>User Dashboard</h2>
        <p>Welcome, ${user.fullName}!</p>
        
        <div class="row mt-4">
            <!-- Bug Reporting -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-primary text-white">
                        <h5><i class="fas fa-bug"></i> Bug Reporting</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="bug?action=new" class="list-group-item list-group-item-action">
                                <i class="fas fa-plus"></i> Report New Bug
                            </a>
                            <a href="bug?action=mybugs" class="list-group-item list-group-item-action">
                                <i class="fas fa-list"></i> My Reported Bugs
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Bug Tracking -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-info text-white">
                        <h5><i class="fas fa-search"></i> Track Bug Status</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="bug?action=status" class="list-group-item list-group-item-action">
                                <i class="fas fa-tasks"></i> View Bug Status Updates
                            </a>
                            <a href="bug?action=replies" class="list-group-item list-group-item-action">
                                <i class="fas fa-comments"></i> View Developer/Admin Replies
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
                        <h3>${reportedBugs}</h3>
                        <p>Reported Bugs</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-4">
                <div class="card bg-success text-white">
                    <div class="card-body text-center">
                        <h3>${resolvedBugs}</h3>
                        <p>Resolved Bugs</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-4">
                <div class="card bg-warning text-dark">
                    <div class="card-body text-center">
                        <h3>${pendingBugs}</h3>
                        <p>Pending Bugs</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- My Reported Bugs -->
        <div class="card mt-4">
            <div class="card-header bg-primary text-white">
                <h5><i class="fas fa-bug"></i> My Reported Bugs</h5>
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
                                <th>Assigned To</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="bug" items="${myReportedBugs}">
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
                                    <td>${bug.assignedToName}</td>
                                    <td>
                                        <a href="bug?action=view&id=${bug.bugId}" class="btn btn-info btn-sm">View</a>
                                        <a href="bug?action=feedback&id=${bug.bugId}" class="btn btn-warning btn-sm">Add Feedback</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <!-- Recent Comments -->
        <div class="card mt-4">
            <div class="card-header bg-info text-white">
                <h5><i class="fas fa-comments"></i> Recent Comments</h5>
            </div>
            <div class="card-body">
                <div class="list-group">
                    <c:forEach var="comment" items="${recentComments}">
                        <div class="list-group-item">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">Bug #${comment.bugId}: ${comment.bugTitle}</h5>
                                <small>${comment.createdDate}</small>
                            </div>
                            <p class="mb-1">${comment.comment}</p>
                            <small>By ${comment.username}</small>
                        </div>
                    </c:forEach>
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