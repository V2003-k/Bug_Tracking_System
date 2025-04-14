<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${project.projectName} - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="card mb-4">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h4>Project Details</h4>
                <div>
                    <a href="project?action=edit&id=${project.projectId}" class="btn btn-warning">Edit Project</a>
                    <a href="project?action=list" class="btn btn-secondary">Back to Projects</a>
                </div>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <h5>Project Information</h5>
                        <table class="table table-bordered">
                            <tr>
                                <th style="width: 30%">Project ID</th>
                                <td>${project.projectId}</td>
                            </tr>
                            <tr>
                                <th>Name</th>
                                <td>${project.projectName}</td>
                            </tr>
                            <tr>
                                <th>Description</th>
                                <td>${project.description}</td>
                            </tr>
                            <tr>
                                <th>Start Date</th>
                                <td>${project.startDate}</td>
                            </tr>
                            <tr>
                                <th>End Date</th>
                                <td>${project.endDate}</td>
                            </tr>
                            <tr>
                                <th>Status</th>
                                <td>
                                    <span class="badge ${project.status == 'ACTIVE' ? 'badge-success' : project.status == 'COMPLETED' ? 'badge-primary' : 'badge-warning'}">
                                        ${project.status}
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <h5>Project Statistics</h5>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <div class="card bg-primary text-white">
                                    <div class="card-body">
                                        <h5 class="card-title">Total Bugs</h5>
                                        <h2 class="card-text">${projectBugs.size()}</h2>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <div class="card bg-success text-white">
                                    <div class="card-body">
                                        <h5 class="card-title">Fixed Bugs</h5>
                                        <h2 class="card-text">
                                            <c:set var="fixedCount" value="0" />
                                            <c:forEach var="bug" items="${projectBugs}">
                                                <c:if test="${bug.status == 'FIXED' || bug.status == 'CLOSED'}">
                                                    <c:set var="fixedCount" value="${fixedCount + 1}" />
                                                </c:if>
                                            </c:forEach>
                                            ${fixedCount}
                                        </h2>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <div class="card bg-warning text-white">
                                    <div class="card-body">
                                        <h5 class="card-title">In Progress</h5>
                                        <h2 class="card-text">
                                            <c:set var="inProgressCount" value="0" />
                                            <c:forEach var="bug" items="${projectBugs}">
                                                <c:if test="${bug.status == 'IN_PROGRESS'}">
                                                    <c:set var="inProgressCount" value="${inProgressCount + 1}" />
                                                </c:if>
                                            </c:forEach>
                                            ${inProgressCount}
                                        </h2>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <div class="card bg-danger text-white">
                                    <div class="card-body">
                                        <h5 class="card-title">Open Bugs</h5>
                                        <h2 class="card-text">
                                            <c:set var="openCount" value="0" />
                                            <c:forEach var="bug" items="${projectBugs}">
                                                <c:if test="${bug.status == 'OPEN'}">
                                                    <c:set var="openCount" value="${openCount + 1}" />
                                                </c:if>
                                            </c:forEach>
                                            ${openCount}
                                        </h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h4>Project Bugs</h4>
                <a href="bug?action=new&projectId=${project.projectId}" class="btn btn-primary">Add New Bug</a>
            </div>
            <div class="card-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Reported By</th>
                            <th>Assigned To</th>
                            <th>Status</th>
                            <th>Priority</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bug" items="${projectBugs}">
                            <tr>
                                <td>${bug.bugId}</td>
                                <td>${bug.title}</td>
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
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>