<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${project.projectName} - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h4>${project.projectName}</h4>
                <div>
                    <a href="project?action=edit&id=${project.projectId}" class="btn btn-primary">Edit Project</a>
                    <a href="project?action=list" class="btn btn-secondary">Back to List</a>
                </div>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8">
                        <h5>Description</h5>
                        <p>${project.description}</p>
                        
                        <h5 class="mt-4">Bugs</h5>
                        <div class="mb-3">
                            <a href="bug?action=new&projectId=${project.projectId}" class="btn btn-success">Report New Bug</a>
                        </div>
                        
                        <c:if test="${empty bugs}">
                            <p class="text-muted">No bugs reported for this project yet.</p>
                        </c:if>
                        
                        <c:if test="${not empty bugs}">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Title</th>
                                            <th>Status</th>
                                            <th>Priority</th>
                                            <th>Assigned To</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="bug" items="${bugs}">
                                            <tr>
                                                <td>${bug.bugId}</td>
                                                <td>${bug.title}</td>
                                                <td>
                                                    <span class="badge ${bug.status == 'Open' ? 'badge-danger' : bug.status == 'In Progress' ? 'badge-warning' : 'badge-success'}">
                                                        ${bug.status}
                                                    </span>
                                                </td>
                                                <td>
                                                    <span class="badge ${bug.priority == 'High' ? 'badge-danger' : bug.priority == 'Medium' ? 'badge-warning' : 'badge-info'}">
                                                        ${bug.priority}
                                                    </span>
                                                </td>
                                                <td>${bug.assignedToName}</td>
                                                <td>
                                                    <a href="bug?action=view&id=${bug.bugId}" class="btn btn-sm btn-info">View</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <a href="bug?action=list&projectId=${project.projectId}" class="btn btn-primary">View All Bugs</a>
                        </c:if>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-header">
                                <h5>Project Details</h5>
                            </div>
                            <div class="card-body">
                                <table class="table">
                                    <tr>
                                        <th>Status:</th>
                                        <td>
                                            <span class="badge ${project.status == 'Active' ? 'badge-success' : project.status == 'On Hold' ? 'badge-warning' : 'badge-secondary'}">
                                                ${project.status}
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Start Date:</th>
                                        <td><fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd" /></td>
                                    </tr>
                                    <tr>
                                        <th>End Date:</th>
                                        <td>
                                            <c:if test="${project.endDate != null}">
                                                <fmt:formatDate value="${project.endDate}" pattern="yyyy-MM-dd" />
                                            </c:if>
                                            <c:if test="${project.endDate == null}">
                                                Not specified
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Total Bugs:</th>
                                        <td>${bugs.size()}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        
                        <div class="card mt-4">
                            <div class="card-header">
                                <h5>Bug Statistics</h5>
                            </div>
                            <div class="card-body">
                                <canvas id="bugStatusChart"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
    <script>
        // Count bugs by status
        var openCount = 0;
        var inProgressCount = 0;
        var fixedCount = 0;
        var closedCount = 0;
        
        <c:forEach var="bug" items="${bugs}">
            <c:if test="${bug.status == 'Open'}">
                openCount++;
            </c:if>
            <c:if test="${bug.status == 'In Progress'}">
                inProgressCount++;
            </c:if>
            <c:if test="${bug.status == 'Fixed'}">
                fixedCount++;
            </c:if>
            <c:if test="${bug.status == 'Closed'}">
                closedCount++;
            </c:if>
        </c:forEach>
        
        // Create chart
        var ctx = document.getElementById('bugStatusChart').getContext('2d');
        var bugStatusChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: ['Open', 'In Progress', 'Fixed', 'Closed'],
                datasets: [{
                    data: [openCount, inProgressCount, fixedCount, closedCount],
                    backgroundColor: [
                        '#dc3545', // Red for Open
                        '#ffc107', // Yellow for In Progress
                        '#17a2b8', // Cyan for Fixed
                        '#28a745'  // Green for Closed
                    ]
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    </script>
</body>
</html>