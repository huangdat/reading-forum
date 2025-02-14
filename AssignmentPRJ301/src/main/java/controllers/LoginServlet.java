package controllers;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import DAO.LoginDAO;
import DAO.accountDAO;
import Model.Register;
import Model.User;
import Model.login;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * This class is responsible for handling the login page
 * Handling all URL requests that start with /LoginServlet
 */
@WebServlet(name = "LoginServlet", value = "/LoginServlet/*")
public class LoginServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get the path from the request URI
        String path = request.getRequestURI();

        // Handling different paths and forward to the appropriate page
        HttpSession session = request.getSession();

        switch (path) {
            case "/LoginServlet":
                // Check if the user is logged in before forwarding to the main page
                if (session.getAttribute("user") != null) {
                    request.getRequestDispatcher("/mainWed.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/LoginServlet/guest");
                }
                break;
            case "/LoginServlet/Login":
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;
            case "/LoginServlet/Register":
                request.getRequestDispatcher("/Register.jsp").forward(request, response);
                break;
            // If the user is not logged in, redirect to the guest page. Remove the user session and cookie.
            case "/LoginServlet/guest":
                session = request.getSession();
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie ck : cookies) {
                        if (ck.getName().equals("user")) {
                            ck.setValue("");
                            ck.setMaxAge(0);
                            response.addCookie(ck);
                        }
                    }
                }
                session.removeAttribute("user");
                session.removeAttribute("Admin");
                request.getRequestDispatcher("/guestPage.jsp").forward(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // URL non valide
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        /*
         * If the user clicks the login button, the username and password are retrieved from the form and checked in the database.
         * If the user exists, the user is redirected to the main page.
         * If the user does not exist, an error message is displayed.
         */
        if (request.getParameter("btnLogin") != null) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            LoginDAO loginDAO = new LoginDAO();

//            login lg = new login();
            User user = new User();

            user.setUsername(username);
            user.setPassword(password);

//            String username = dao.getUsername(lg);

            String errorLogin = "Sorry! Wrong username or password. Please try again.";

            /*
             * If the user is an admin, the user is redirected to the admin page.
             * If the user is not an admin, the user is redirected to the main page.
             */
            if (loginDAO.isAdmin(user)) {
                Cookie userCookie = new Cookie("user", username);
                // Max age of cookie is 30 days (60s * 60m * 24h * 30d)
                userCookie.setMaxAge(60 * 60 * 24 * 30);
                response.addCookie(userCookie);
                session = request.getSession();
                session.setAttribute("user", username);
                session.setAttribute("Admin", username);
                accountDAO daoAcc = new accountDAO();
                ResultSet rsUser = daoAcc.getUser(username);
                String user_id = "";
                String avatar = "";
                try {
                    if (rsUser.next()) {
                        user_id = rsUser.getString("user_id");
                        avatar = rsUser.getString("avatar");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                session.setAttribute("userId", user_id);
                session.setAttribute("avtUser", avatar);
                response.sendRedirect("/LoginServlet");
            } else if (loginDAO.isUser(user)) {
                System.out.println("controllers.LoginController.doPost()1234");
                Cookie userCookie = new Cookie("user", username);
                // Max age of cookie is 30 days (60s * 60m * 24h * 30d)
                userCookie.setMaxAge(60 * 60 * 24 * 30);
                response.addCookie(userCookie);
                session.setAttribute("user", username);
                accountDAO daoAcc = new accountDAO();
                ResultSet rsUser = daoAcc.getUser(username);
                String userId = "";
                String avtUser = "";
                try {
                    if (rsUser.next()) {
                        userId = rsUser.getString("user_id");
                        avtUser = rsUser.getString("avatar");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                session.setAttribute("userId", userId);
                session.setAttribute("avtUser", avtUser);

                response.sendRedirect("/LoginServlet");
            } else {
                session.setAttribute("errorLogin", errorLogin);

                response.sendRedirect("/LoginServlet/Login");
            }
        }



        /*
         * If the user clicks the register button, the email, username, password, and confirm password are retrieved from the form.
         * The password and confirm password are checked to see if they match.
         * If the passwords match, the email and username are checked in the database.
         * If the email and username do not exist, the user is registered and redirected to the login page.
         * If the email and username exist, an error message is displayed.
         */
        if (request.getParameter("btnRegister") != null) {
            String email = request.getParameter("email");
            String reuser = request.getParameter("reuser");
            String repass = request.getParameter("repass");
            String confirmpass = request.getParameter("confirmpass");
            String errorRegister = "email hoặc username này đã tồn tại.";
            String errorConfirmpass = "Password confirmation does not match the password";
            String test = "";
            if (repass.equals(confirmpass)) {
                Register lo = new Register();
                lo.setEmail(email);
                lo.setUser(reuser);
                lo.setPass(repass);
                LoginDAO dao = new LoginDAO();
                if (dao.checkRegister(lo)) {
                    if (dao.register(lo)) {
                        response.sendRedirect("/LoginServlet/Login");
                    } else {

                        response.sendRedirect("/LoginController/Register");
                    }
                } else {
                    session.setAttribute("errorRegister", errorRegister);

                    response.sendRedirect("/LoginServlet/Register");
                }
            } else {
                session.setAttribute("errorConfirmpass", errorConfirmpass);

                response.sendRedirect("/LoginServlet/Register");
            }
        }

    }


    public void destroy() {
    }
}