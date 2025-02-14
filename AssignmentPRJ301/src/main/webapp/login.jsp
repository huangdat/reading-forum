<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: "Arial", sans-serif;
        }

        .login-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-image: url("/imgs/anhbackground.jpeg"); /* Ensure this path is correct */
            background-size: cover;
            background-position: center;
        }

        .login-card {
            background-color: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 400px;
        }

        .btn-custom {
            background-color: #d9534f; /* Button color */
            color: white;
            font-size: 16px;
            border-radius: 5px;
        }

        .btn-custom:hover {
            background-color: #c9302c;
        }

        .form-link {
            color: #d9534f;
            text-decoration: none;
            font-size: 14px;
        }

        .form-link:hover {
            text-decoration: underline;
        }

        .error-message {
            color: red;
            font-size: 12px;
        }
    </style>
</head>
<body>
<%
    // Retrieve error messages from session (if any)
    String errorLogin = (String) session.getAttribute("errorLogin");
    session.setAttribute("errorLogin", null); // Clear the error after retrieving
%>

<div class="login-container">
    <div class="login-card" style="background-color: rgba(255, 255, 255, 0.29); ">
        <form method="post" action="LoginServlet"> <!-- Updated action here -->
            <h2 class="text-center mb-4"><strong>Welcome</strong></h2>
            <% if (errorLogin != null && !errorLogin.isEmpty()) { %>
            <div class="alert alert-danger" role="alert"><%= errorLogin %>
            </div>
            <% } %>

            <div class="form-floating mb-3">
                <%--                <label for="username" class="form-label">Username</label>--%>
                <input type="text" name="username" class="form-control" id="username" required>
                <label for="username">Username</label>

            </div>

            <div class="form-floating mb-3">
                <%--                <label for="password" class="form-label">Password</label>--%>
                <input type="password" name="password" class="form-control" id="password" required>
                <label for="password">Password</label>
            </div>


            <%--            <div class="form-floating mb-3">--%>
            <%--                <input type="text" class="form-control" id="txtUser" name="txtUser"/>--%>
            <%--                <label for="txtUser">Username</label>--%>
            <%--            </div>--%>
            <%--            <div class="form-floating mb-3">--%>
            <%--                <input type="password" class="form-control" id="txtPass" name="txtPass"/>--%>
            <%--                <label for="txtPass">Password</label>--%>
            <%--            </div>--%>

            <button type="submit" name="btnLogin" class="btn btn-custom w-100" style="background-color: rgba(85,255,121,0.81);">Login
            </button>
            <div class="text-center mt-3">
                <span>Don't have an account? </span>
                <a href="/LoginServlet/Register" class="form-link">Register here</a>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
