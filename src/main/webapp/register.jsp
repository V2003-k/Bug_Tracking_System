<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Register</title>
    <style>
      .error {
        color: red;
        margin-bottom: 10px;
      }
    </style>
  </head>
  <body>
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
  </body>
</html>
