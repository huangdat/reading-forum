<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        /* Additional CSS */
        body {
            background-color: #f8f9fa;

            font-family: "Arial", sans-serif;
        }
        .register-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-image: url("/imgs/anhbackground.jpeg"); /* Same background as login */
            background-size: cover;
            background-position: center;
        }
        .register-card {
            background-color: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        .btn-custom {
            background-color: #d9534f; /* Same button style as login */
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
    String errorConfirmpass = (String) session.getAttribute("errorConfirmpass");
    String errorRegister = (String) session.getAttribute("errorRegister");
    session.setAttribute("errorConfirmpass", "");
    session.setAttribute("errorRegister", "");
%>

<div class="register-container "  style="background-color: rgba(255, 255, 255, 0.29);">
    <div class="register-card" style="background-color: rgba(255, 255, 255, 0.29);">
        <form method="post" action="LoginController">
            <h2 class="text-center mb-4">Register</h2>

            <% if (errorConfirmpass != null && !errorConfirmpass.isEmpty()) { %>
            <div class="alert alert-danger" role="alert"><%= errorConfirmpass %></div>
            <% }
                if (errorRegister != null && !errorRegister.isEmpty()) { %>
            <div class="alert alert-danger" role="alert"><%= errorRegister %></div>
            <% } %>

            <div class="form-floating mb-3">
                <input type="text" name="reuser" class="form-control" id="reuser" required>
                <label for="reuser" class="form-label">Username</label>
            </div>

            <div class="form-floating mb-3">
                <input type="email" name="email" class="form-control" id="email" required>
                <label for="email" class="form-label">Email</label>
                <span id="emailError" class="error-message"></span>
            </div>

            <div class="form-floating mb-3">
                <input type="password" name="repass" class="form-control" id="repass" required>
                <label for="repass" class="form-label">Password</label>
            </div>

            <div class="form-floating mb-3">
                <input type="password" name="confirmpass" class="form-control" id="confirmpass" required>
                <label for="confirmpass" class="form-label">Confirm Password</label>
            </div>

            <button type="submit" name="btnRegister" class="btn btn-custom w-100">Register</button>

            <div class="text-center mt-3">
                <span>Already have an account? </span>
                <a href="/LoginServlet/Login" class="form-link">Login here</a>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN6jIeHz"
        crossorigin="anonymous"></script>

<script>
    document.getElementById('email').addEventListener('input', function() {
        const emailInput = this;
        const emailError = document.getElementById('emailError');
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailRegex.test(emailInput.value)) {
            emailError.textContent = "Please enter a valid email address.";
            emailError.style.display = "block";
        } else {
            emailError.textContent = "";
            emailError.style.display = "none";
        }
    });
</script>
</body>
</html>
