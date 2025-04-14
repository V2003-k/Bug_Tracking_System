<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${project != null ? 'Edit Project' : 'Add New Project'} - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h4>${project != null ? 'Edit Project' : 'Add New Project'}</h4>
            </div>
            <div class="card-body">
                <form action="project" method="post">
                    <input type="hidden" name="action" value="${project != null ? 'update' : 'create'}" />
                    <c:if test="${project != null}">
                        <input type="hidden" name="projectId" value="${project.projectId}" />
                    </c:if>
                    
                    <div class="form-group">
                        <label for="projectName">Project Name</label>
                        <input type="text" class="form-control" id="projectName" name="projectName" value="${project != null ? project.projectName : ''}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="3">${project != null ? project.description : ''}</textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="startDate">Start Date</label>
                        <input type="date" class="form-control" id="startDate" name="startDate" value="${project != null ? project.startDate : ''}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="endDate">End Date</label>
                        <input type="date" class="form-control" id="endDate" name="endDate" value="${project != null ? project.endDate : ''}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-control" id="status" name="status" required>
                            <option value="ACTIVE" ${project != null && project.status == 'ACTIVE' ? 'selected' : ''}>Active</option>
                            <option value="COMPLETED" ${project != null && project.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                            <option value="ON_HOLD" ${project != null && project.status == 'ON_HOLD' ? 'selected' : ''}>On Hold</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">${project != null ? 'Update' : 'Create'} Project</button>
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