<%@ page language="java" contentType="text/html; charset=UTF-8"
<<<<<<< HEAD
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Register</title>
    <style>
      .error {
        color: red;
        margin-bottom: 10px;
=======
pageEncoding="UTF-8"%>
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
>>>>>>> 9e79a15 (almost done)
      }
    </style>
  </head>
  <body>
<<<<<<< HEAD
    <h2>Register</h2>

    <% String error = request.getParameter("error"); if (error != null) { String
    errorMessage = ""; switch(error) { case "email_exists": errorMessage = "This
    email address is already registered. Please use a different email."; break;
    case "invalid_input": errorMessage = "Please fill in all fields."; break;
    case "invalid_role": errorMessage = "Invalid role selected."; break; case
    "database_error": errorMessage = "A database error occurred. Please try
    again."; break; case "server_error": errorMessage = "A server error
    occurred. Please try again later."; break; default: errorMessage =
    "Registration failed. Please try again."; } %>
    <div class="error"><%= errorMessage %></div>
    <% } %>

    <form action="RegisterServlet" method="post">
      Name: <input type="text" name="name" required /><br />
      Email: <input type="email" name="email" required /><br />
      Password: <input type="password" name="password" required /><br />
      Role:
      <select name="role">
        <option value="Admin">Admin</option>
        <option value="Developer">Developer</option>
        <option value="Tester">Tester</option>
      </select>
      <br />
      <input type="submit" value="Register" />
    </form>
=======
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

              <form action="register" method="post">
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
                Already have an account? <a href="login.jsp">Login</a>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
>>>>>>> 9e79a15 (almost done)
  </body>
</html>
