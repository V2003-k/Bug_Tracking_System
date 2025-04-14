<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <h2>Admin Dashboard</h2>
        <p>Welcome, ${user.fullName}!</p>
        
        <div class="row mt-4">
            <!-- Bug Management -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-primary text-white">
                        <h5><i class="fas fa-bug"></i> Bug Management</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="bug?action=list" class="list-group-item list-group-item-action">
                                <i class="fas fa-list"></i> List All Bugs
                            </a>
                            <a href="bug?action=new" class="list-group-item list-group-item-action">
                                <i class="fas fa-plus"></i> Add New Bug
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Project Management -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-success text-white">
                        <h5><i class="fas fa-project-diagram"></i> Project Management</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="project?action=list" class="list-group-item list-group-item-action">
                                <i class="fas fa-list"></i> List All Projects
                            </a>
                            <a href="project?action=new" class="list-group-item list-group-item-action">
                                <i class="fas fa-plus"></i> Add New Project
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- User Management -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-info text-white">
                        <h5><i class="fas fa-users"></i> User Management</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="user?action=list" class="list-group-item list-group-item-action">
                                <i class="fas fa-list"></i> List All Users
                            </a>
                            <a href="user?action=new" class="list-group-item list-group-item-action">
                                <i class="fas fa-user-plus"></i> Add New User
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Reports -->
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-header bg-warning text-dark">
                        <h5><i class="fas fa-chart-bar"></i> Reports</h5>
                    </div>
                    <div class="card-body">
                        <div class="list-group">
                            <a href="report?type=bugs" class="list-group-item list-group-item-action">
                                <i class="fas fa-bug"></i> Bug Reports
                            </a>
                            <a href="report?type=projects" class="list-group-item list-group-item-action">
                                <i class="fas fa-project-diagram"></i> Project Reports
                            </a>
                            <a href="report?type=users" class="list-group-item list-group-item-action">
                                <i class="fas fa-users"></i> User Reports
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Quick Stats -->
        <div class="row mt-4">
            <div class="col-md-3 mb-4">
                <div class="card bg-primary text-white">
                    <div class="card-body text-center">
                        <h3>${totalBugs}</h3>
                        <p>Total Bugs</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-4">
                <div class="card bg-success text-white">
                    <div class="card-body text-center">
                        <h3>${totalProjects}</h3>
                        <p>Total Projects</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-4">
                <div class="card bg-info text-white">
                    <div class="card-body text-center">
                        <h3>${totalUsers}</h3>
                        <p>Total Users</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-4">
                <div class="card bg-warning text-dark">
                    <div class="card-body text-center">
                        <h3>${openBugs}</h3>
                        <p>Open Bugs</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Recent Activity -->
        <div class="card mt-4">
            <div class="card-header bg-secondary text-white">
                <h5><i class="fas fa-history"></i> Recent Activity</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Activity</th>
                                <th>User</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="activity" items="${recentActivities}">
                                <tr>
                                    <td>${activity.description}</td>
                                    <td>${activity.username}</td>
                                    <td>${activity.date}</td>
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