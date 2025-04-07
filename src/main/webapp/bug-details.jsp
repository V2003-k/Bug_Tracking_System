<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bug #${bug.bugId} - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h4>Bug #${bug.bugId}: ${bug.title}</h4>
                <div>
                    <a href="bug?action=edit&id=${bug.bugId}" class="btn btn-primary">Edit</a>
                    <a href="bug?action=list" class="btn btn-secondary">Back to List</a>
                </div>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8">
                        <h5>Description</h5>
                        <p>${bug.description}</p>
                        
                        <h5 class="mt-4">Comments</h5>
                        <div class="comments-section">
                            <c:if test="${empty comments}">
                                <p class="text-muted">No comments yet.</p>
                            </c:if>
                            
                            <c:forEach var="comment" items="${comments}">
                                <div class="card mb-3">
                                    <div class="card-header bg-light d-flex justify-content-between">
                                        <span><strong>${comment.userName}</strong></span>
                                        <span class="text-muted"><fmt:formatDate value="${comment.commentDate}" pattern="yyyy-MM-dd HH:mm" /></span>
                                    </div>
                                    <div class="card-body">
                                        <p class="card-text">${comment.content}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        
                        <div class="mt-4">
                            <h5>Add Comment</h5>
                            <form action="comment" method="post">
                                <input type="hidden" name="bugId" value="${bug.bugId}">
                                <div class="form-group">
                                    <textarea class="form-control" name="content" rows="3" required></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary">Submit Comment</button>
                            </form>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-header">
                                <h5>Bug Details</h5>
                            </div>
                            <div class="card-body">
                                <table class="table">
                                    <tr>
                                        <th>Project:</th>
                                        <td><a href="project?action=view&id=${bug.projectId}">${bug.projectName}</a></td>
                                    </tr>
                                    <tr>
                                        <th>Status:</th>
                                        <td>
                                            <span class="badge ${bug.status == 'Open' ? 'badge-danger' : bug.status == 'In Progress' ? 'badge-warning' : 'badge-success'}">
                                                ${bug.status}
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Priority:</th>
                                        <td>
                                            <span class="badge ${bug.priority == 'High' ? 'badge-danger' : bug.priority == 'Medium' ? 'badge-warning' : 'badge-info'}">
                                                ${bug.priority}
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Reported By:</th>
                                        <td>${bug.reportedByName}</td>
                                    </tr>
                                    <tr>
                                        <th>Assigned To:</th>
                                        <td>${bug.assignedToName}</td>
                                    </tr>
                                    <tr>
                                        <th>Created Date:</th>
                                        <td><fmt:formatDate value="${bug.createdDate}" pattern="yyyy-MM-dd HH:mm" /></td>
                                    </tr>
                                    <tr>
                                        <th>Updated Date:</th>
                                        <td><fmt:formatDate value="${bug.updatedDate}" pattern="yyyy-MM-dd HH:mm" /></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        
                        <div class="card mt-4">
                            <div class="card-header">
                                <h5>Update Status</h5>
                            </div>
                            <div class="card-body">
                                <form action="bug" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="bugId" value="${bug.bugId}">
                                    
                                    <div class="form-group">
                                        <select class="form-control" name="status" required>
                                            <option value="">Select Status</option>
                                            <option value="Open" ${bug.status == 'Open' ? 'selected' : ''}>Open</option>
                                            <option value="In Progress" ${bug.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                                            <option value="Fixed" ${bug.status == 'Fixed' ? 'selected' : ''}>Fixed</option>
                                            <option value="Closed" ${bug.status == 'Closed' ? 'selected' : ''}>Closed</option>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-block">Update Status</button>
                                </form>
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
</body>
</html>