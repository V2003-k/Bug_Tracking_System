<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${bug != null ? 'Edit Bug' : 'Report Bug'} - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h4>${bug != null ? 'Edit Bug' : 'Report Bug'}</h4>
            </div>
            <div class="card-body">
                <form action="bug" method="post">
                    <input type="hidden" name="action" value="${bug != null ? 'update' : 'create'}" />
                    <c:if test="${bug != null}">
                        <input type="hidden" name="bugId" value="${bug.bugId}" />
                    </c:if>
                    
                    <div class="form-group">
                        <label for="title">Bug Title</label>
                        <input type="text" class="form-control" id="title" name="title" value="${bug != null ? bug.title : ''}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="5" required>${bug != null ? bug.description : ''}</textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="projectId">Project</label>
                        <select class="form-control" id="projectId" name="projectId" required>
                            <option value="">Select Project</option>
                            <c:forEach var="project" items="${projects}">
                                <option value="${project.projectId}" ${bug != null && bug.projectId == project.projectId ? 'selected' : (selectedProjectId != null && selectedProjectId == project.projectId ? 'selected' : '')}>${project.projectName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="assignedTo">Assign To</label>
                        <select class="form-control" id="assignedTo" name="assignedTo" required>
                            <option value="">Select Developer</option>
                            <c:forEach var="developer" items="${developers}">
                                <option value="${developer.userId}" ${bug != null && bug.assignedTo == developer.userId ? 'selected' : ''}>${developer.fullName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-control" id="status" name="status" required>
                            <option value="OPEN" ${bug != null && bug.status == 'OPEN' ? 'selected' : ''}>Open</option>
                            <option value="IN_PROGRESS" ${bug != null && bug.status == 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                            <option value="FIXED" ${bug != null && bug.status == 'FIXED' ? 'selected' : ''}>Fixed</option>
                            <option value="CLOSED" ${bug != null && bug.status == 'CLOSED' ? 'selected' : ''}>Closed</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="priority">Priority</label>
                        <select class="form-control" id="priority" name="priority" required>
                            <option value="LOW" ${bug != null && bug.priority == 'LOW' ? 'selected' : ''}>Low</option>
                            <option value="MEDIUM" ${bug != null && bug.priority == 'MEDIUM' ? 'selected' : ''}>Medium</option>
                            <option value="HIGH" ${bug != null && bug.priority == 'HIGH' ? 'selected' : ''}>High</option>
                            <option value="CRITICAL" ${bug != null && bug.priority == 'CRITICAL' ? 'selected' : ''}>Critical</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">${bug != null ? 'Update' : 'Submit'} Bug</button>
                    <a href="bug?action=list" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>