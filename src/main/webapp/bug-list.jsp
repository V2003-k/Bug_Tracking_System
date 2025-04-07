<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bugs - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>
                <c:if test="${projectName != null}">
                    Bugs for Project: ${projectName}
                </c:if>
                <c:if test="${projectName == null}">
                    All Bugs
                </c:if>
            </h2>
            <div>
                <a href="bug?action=new" class="btn btn-primary">Report New Bug</a>
                <c:if test="${projectId != null}">
                    <a href="project?action=view&id=${projectId}" class="btn btn-secondary">Back to Project</a>
                </c:if>
            </div>
        </div>
        
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Project</th>
                                <th>Reported By</th>
                                <th>Assigned To</th>
                                <th>Status</th>
                                <th>Priority</th>
                                <th>Created Date</th>
                                <th>Action</th>
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
                                        <span class="badge ${bug.status == 'Open' ? 'badge-danger' : bug.status == 'In Progress' ? 'badge-warning' : 'badge-success'}">
                                            ${bug.status}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge ${bug.priority == 'High' ? 'badge-danger' : bug.priority == 'Medium' ? 'badge-warning' : 'badge-info'}">
                                            ${bug.priority}
                                        </span>
                                    </td>
                                    <td><fmt:formatDate value="${bug.createdDate}" pattern="yyyy-MM-dd HH:mm" /></td>
                                    <td>
                                        <a href="bug?action=view&id=${bug.bugId}" class="btn btn-sm btn-info">View</a>
                                        <a href="bug?action=edit&id=${bug.bugId}" class="btn btn-sm btn-primary">Edit</a>
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