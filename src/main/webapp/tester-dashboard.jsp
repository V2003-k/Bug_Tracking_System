<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tester Dashboard - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <h2>Tester Dashboard</h2>
        <p>Welcome, ${user.fullName}!</p>
        
        <div class="row mt-4">
            <!-- Report Bugs -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-danger text-white">
                        <h5><i class="fas fa-bug"></i> Report Bugs</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="bug?action=new" class="list-group-item list-group-item-action">
                                <i class="fas fa-plus"></i> Add New Bug
                            </a>
                            <a href="bug?action=reported" class="list-group-item list-group-item-action">
                                <i class="fas fa-list"></i> View Reported Bugs
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Bug Verification -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-success text-white">
                        <h5><i class="fas fa-check-double"></i> Bug Verification</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="bug?action=fixed" class="list-group-item list-group-item-action">
                                <i class="fas fa-check-circle"></i> Fixed Bugs to Verify
                            </a>
                            <a href="bug?action=closed" class="list-group-item list-group-item-action">
                                <i class="fas fa-check-square"></i> Closed Bugs
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Bug Tracking -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-info text-white">
                        <h5><i class="fas fa-search"></i> View & Track Bugs</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="bug?action=list" class="list-group-item list-group-item-action">
                                <i class="fas fa-list-alt"></i> All Bugs
                            </a>
                            <a href="project?action=list" class="list-group-item list-group-item-action">
                                <i class="fas fa-project-diagram"></i> View Projects
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Reports -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-warning text-dark">
                        <h5><i class="fas fa-chart-bar"></i> Bug Reports</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="report?type=submitted" class="list-group-item list-group-item-action">
                                <i class="fas fa-file-alt"></i> Submitted Bugs Report
                            </a>
                            <a href="report?type=history" class="list-group-item list-group-item-action">
                                <i class="fas fa-history"></i> Bug History
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Quick Stats -->
        <div class="row mt-4">
            <div class="col-md-3 mb-4">
                <div class="card bg-danger text-white">
                    <div class="card-body text-center">
                        <h3>${reportedBugs}</h3>
                        <p>Reported Bugs</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-4">
                <div class="card bg-warning text-dark">
                    <div class="card-body text-center">
                        <h3>${fixedBugsToVerify}</h3>
                        <p>To Verify</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-4">
                <div class="card bg-success text-white">
                    <div class="card-body text-center">
                        <h3>${verifiedBugs}</h3>
                        <p>Verified Bugs</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-4">
                <div class="card bg-info text-white">
                    <div class="card-body text-center">
                        <h3>${reopenedBugs}</h3>
                        <p>Reopened Bugs</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Recently Reported Bugs -->
        <div class="card mt-4">
            <div class="card-header bg-danger text-white">
                <h5><i class="fas fa-bug"></i> Recently Reported Bugs</h5>
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
                            <c:forEach var="bug" items="${recentReportedBugs}">
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
                                        <a href="bug?action=edit&id=${bug.bugId}" class="btn btn-warning btn-sm">Edit</a>
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