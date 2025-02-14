package controllers;

import DAO.editNovelDAO;
import Model.novelInfor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author LENOVO
 */

@MultipartConfig // Add this annotation
@WebServlet(name = "EditPostServlet", urlPatterns = {"/EditPostServlet/*"})
public class EditPostServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet novelController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet novelController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            novelInfor novel = null;
            try {
                editNovelDAO dao = new editNovelDAO();
                novel = dao.getNovelInfor(id);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("ManagePostServlet");
                return;
            }

            if (novel == null) {
                response.sendRedirect("ManagePostServlet");
            } else {
                request.setAttribute("novel", novel);
                request.getRequestDispatcher("editPost.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("ManagePostServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getParameter("btnSave") != null) {

            String title = req.getParameter("title");
            String author = req.getParameter("author");
            String status = req.getParameter("status");
            String content = req.getParameter("description");

            if (title != null && author != null && status != null && content != null) {
                novelInfor novel = new novelInfor();
                novel.setNovel_title(title);
                novel.setAuthor(author);
                novel.setStatus(status);
                novel.setDescription(content);
                editNovelDAO dao = new editNovelDAO();
                int count = dao.updateNovel(novel, req.getParameter("id"));
                resp.reset();
                if (count > 0) {
                    resp.sendRedirect("ManagePostServlet");
                } else {
                    resp.sendRedirect("EditPostServlet?id=" + req.getParameter("id"));
                }
            } else {
                resp.sendRedirect("EditPostServlet?id=" + req.getParameter("id"));
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String handleFileUpload(Part cover) throws IOException {
        String fileName = Paths.get(cover.getSubmittedFileName()).getFileName().toString();
        String uploadDir = "covers"; // Specify your upload directory
        Path uploadPath = Paths.get(getServletContext().getRealPath(""), uploadDir);
        // Generate a unique file name to prevent overwriting
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        Path filePath = uploadPath.resolve(uniqueFileName);
        // Save the uploaded file to the server
        try (InputStream inputStream = cover.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        // Return the relative path to the uploaded file
        return uploadDir + "/" + uniqueFileName;
    }
}
