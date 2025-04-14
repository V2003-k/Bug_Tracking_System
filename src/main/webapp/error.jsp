<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Bug Tracking System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card text-center">
                    <div class="card-header bg-danger text-white">
                        <h4>Error</h4>
                    </div>
                    <div class="card-body">
                        <div class="mb-4">
                            <i class="fas fa-exclamation-triangle fa-5x text-danger"></i>
                        </div>
                        <h5 class="card-title">Oops! Something went wrong.</h5>
                        <p class="card-text">We apologize for the inconvenience. Please try again later or contact the system administrator if the problem persists.</p>
                        <div class="mt-4">
                            <a href="dashboard" class="btn btn-primary mr-2">Go to Dashboard</a>
                            <a href="login.jsp" class="btn btn-secondary">Go to Login</a>
                        </div>
                    </div>
                    <div class="card-footer text-muted">
                        Error Code: ${pageContext.errorData.statusCode}
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"></script>
</body>
</html>