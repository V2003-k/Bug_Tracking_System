<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Register - Bug Tracking System</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap"
      rel="stylesheet"
    />
    <style>
      body {
        font-family: "Roboto", sans-serif;
        background-color: #f8f9fa;
      }
      .card {
        border: none;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
      }
      .card-header {
        background-color: #f8f9fa;
        border-bottom: 1px solid #eee;
        font-weight: 500;
      }
      .form-control {
        border-radius: 5px;
      }
      .btn-primary {
        background-color: #007bff;
        border: none;
        border-radius: 5px;
        padding: 10px 20px;
        font-weight: 500;
      }
      .btn-primary:hover {
        background-color: #0069d9;
      }
    </style>
  </head>
  <body>
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card">
            <div class="card-header">
              <h4>Register</h4>
            </div>
            <div class="card-body">
              <% if(request.getAttribute("error") != null) { %>
              <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
              </div>
              <% } %>

              <form action="<%=request.getContextPath()%>/register" method="post">
                <div class="form-group">
                  <label for="username">Username</label>
                  <input
                    type="text"
                    class="form-control"
                    id="username"
                    name="username"
                    required
                  />
                </div>

                <div class="form-group">
                  <label for="email">Email</label>
                  <input
                    type="email"
                    class="form-control"
                    id="email"
                    name="email"
                    required
                  />
                </div>

                <div class="form-group">
                  <label for="fullName">Full Name</label>
                  <input
                    type="text"
                    class="form-control"
                    id="fullName"
                    name="fullName"
                    required
                  />
                </div>

                <div class="form-group">
                  <label for="role">Role</label>
                  <select class="form-control" id="role" name="role" required>
                    <option value="USER">User</option>
                    <option value="DEVELOPER">Developer</option>
                    <option value="TESTER">Tester</option>
                    <option value="ADMIN">Admin</option>
                  </select>
                </div>

                <div class="form-group">
                  <label for="password">Password</label>
                  <input
                    type="password"
                    class="form-control"
                    id="password"
                    name="password"
                    required
                  />
                </div>

                <div class="form-group">
                  <label for="confirmPassword">Confirm Password</label>
                  <input
                    type="password"
                    class="form-control"
                    id="confirmPassword"
                    name="confirmPassword"
                    required
                  />
                </div>

                <button type="submit" class="btn btn-primary btn-block">
                  Register
                </button>
              </form>
            </div>
            <div class="card-footer text-center">
              <p class="mb-0">
                Already have an account? <a href="<%=request.getContextPath()%>/login.jsp">Login</a>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  </body>
</html>
