<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${project != null ? 'Edit' : 'Create'} Project - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h4>${project != null ? 'Edit Project' : 'Create New Project'}</h4>
            </div>
            <div class="card-body">
                <form action="project" method="post">
                    <input type="hidden" name="action" value="${project != null ? 'update' : 'create'}">
                    <c:if test="${project != null}">
                        <input type="hidden" name="projectId" value="${project.projectId}">
                    </c:if>
                    
                    <div class="form-group">
                        <label for="projectName">Project Name</label>
                        <input type="text" class="form-control" id="projectName" name="projectName" value="${project != null ? project.projectName : ''}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="5">${project != null ? project.description : ''}</textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="startDate">Start Date</label>
                        <input type="date" class="form-control" id="startDate" name="startDate" 
                               value="${project != null ? project.startDate : ''}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="endDate">End Date</label>
                        <input type="date" class="form-control" id="endDate" name="endDate" 
                               value="${project != null ? project.endDate : ''}">
                    </div>
                    
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-control" id="status" name="status" required>
                            <option value="">Select Status</option>
                            <option value="Active" ${project != null && project.status == 'Active' ? 'selected' : ''}>Active</option>
                            <option value="On Hold" ${project != null && project.status == 'On Hold' ? 'selected' : ''}>On Hold</option>
                            <option value="Completed" ${project != null && project.status == 'Completed' ? 'selected' : ''}>Completed</option>
                            <option value="Cancelled" ${project != null && project.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">${project != null ? 'Update' : 'Create'}</button>
                    <a href="project?action=list" class="btn btn-secondary">Cancel</a>
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