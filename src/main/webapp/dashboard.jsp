<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Dashboard - Bug Tracking System</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <link rel="stylesheet" href="css/style.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
    />
  </head>
  <body>
    <jsp:include page="header.jsp" />

    <div class="container mt-4">
      <h2>Dashboard</h2>

      <div class="row mt-4">
        <div class="col-md-4">
          <div class="card text-white bg-primary mb-3">
            <div class="card-body">
              <div class="d-flex justify-content-between align-items-center">
                <div>
                  <h5 class="card-title">Assigned Bugs</h5>
                  <h2 class="card-text">${assignedBugsCount}</h2>
                </div>
                <i class="fas fa-tasks fa-3x"></i>
              </div>
              <a href="bug?action=list" class="btn btn-light btn-sm mt-3"
                >View All</a
              >
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card text-white bg-danger mb-3">
            <div class="card-body">
              <div class="d-flex justify-content-between align-items-center">
                <div>
                  <h5 class="card-title">Open Bugs</h5>
                  <h2 class="card-text">${openBugsCount}</h2>
                </div>
                <i class="fas fa-exclamation-circle fa-3x"></i>
              </div>
              <a href="bug?action=list" class="btn btn-light btn-sm mt-3"
                >View All</a
              >
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card text-white bg-success mb-3">
            <div class="card-body">
              <div class="d-flex justify-content-between align-items-center">
                <div>
                  <h5 class="card-title">Resolved Bugs</h5>
                  <h2 class="card-text">${resolvedBugsCount}</h2>
                </div>
                <i class="fas fa-check-circle fa-3x"></i>
              </div>
              <a href="bug?action=list" class="btn btn-light btn-sm mt-3"
                >View All</a
              >
            </div>
          </div>
        </div>
      </div>

      <div class="row mt-4">
        <div class="col-md-8">
          <div class="card">
            <div class="card-header">
              <h5>Recent Bugs</h5>
            </div>
            <div class="card-body">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Priority</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="bug" items="${recentBugs}">
                    <tr>
                      <td>${bug.bugId}</td>
                      <td>${bug.title}</td>
                      <td>
                        <span
                          class="badge ${bug.status == 'Open' ? 'badge-danger' : bug.status == 'In Progress' ? 'badge-warning' : 'badge-success'}"
                        >
                          ${bug.status}
                        </span>
                      </td>
                      <td>
                        <span
                          class="badge ${bug.priority == 'High' ? 'badge-danger' : bug.priority == 'Medium' ? 'badge-warning' : 'badge-info'}"
                        >
                          ${bug.priority}
                        </span>
                      </td>
                      <td>
                        <a
                          href="bug?action=view&id=${bug.bugId}"
                          class="btn btn-sm btn-info"
                          >View</a
                        >
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <a href="bug?action=list" class="btn btn-primary"
                >View All Bugs</a
              >
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card">
            <div class="card-header">
              <h5>Quick Actions</h5>
            </div>
            <div class="card-body">
              <div class="list-group">
                <a
                  href="bug?action=new"
                  class="list-group-item list-group-item-action"
                >
                  <i class="fas fa-plus-circle mr-2"></i> Report New Bug
                </a>
                <a
                  href="project?action=new"
                  class="list-group-item list-group-item-action"
                >
                  <i class="fas fa-folder-plus mr-2"></i> Create New Project
                </a>
                <a
                  href="user?action=profile"
                  class="list-group-item list-group-item-action"
                >
                  <i class="fas fa-user-edit mr-2"></i> Update Profile
                </a>
              </div>
            </div>
          </div>

          <div class="card mt-4">
            <div class="card-header">
              <h5>My Projects</h5>
            </div>
            <div class="card-body">
              <div class="list-group">
                <c:forEach
                  var="project"
                  items="${userProjects}"
                  varStatus="loop"
                >
                  <c:if test="${loop.index < 5}">
                    <a
                      href="project?action=view&id=${project.projectId}"
                      class="list-group-item list-group-item-action"
                    >
                      ${project.projectName}
                    </a>
                  </c:if>
                </c:forEach>
              </div>
              <a href="project?action=list" class="btn btn-primary btn-sm mt-3"
                >View All Projects</a
              >
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
