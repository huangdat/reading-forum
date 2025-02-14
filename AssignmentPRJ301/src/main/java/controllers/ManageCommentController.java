package controllers;

import DAO.CommentDAO;
import Model.CommentDelete;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/CommentController"})
public class ManageCommentController extends HttpServlet {

    private CommentDAO commentDAO;

    @Override
    public void init() throws ServletException {
        commentDAO = new CommentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "delete":
                deleteComment(request, response);
                break;
            case "search":
                searchComments(request, response);
                break;
            default:
                listComments(request, response);
                break;
        }
    }

    private void deleteComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        commentDAO.deleteComment(id);
        response.sendRedirect(request.getContextPath() + "/CommentController");
    }

    private void searchComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<CommentDelete> comments = commentDAO.getCommentsBySearchAndPage(query, page);
        int totalPages = commentDAO.getTotalPages(query);

        request.setAttribute("listComment", comments);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("query", query);
        request.getRequestDispatcher("manage_comments.jsp").forward(request, response);
    }

    private void listComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<CommentDelete> comments = commentDAO.getCommentsBySearchAndPage("", page);
        int totalPages = commentDAO.getTotalPages("");

        request.setAttribute("listComment", comments);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("manage_comments.jsp").forward(request, response);
    }
}
