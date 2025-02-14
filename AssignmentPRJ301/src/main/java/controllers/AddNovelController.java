package controllers;

import DAO.editNovelDAO;
import Model.novelGenre;
import Model.novelInfor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author hp
 */
public class AddNovelController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditPostServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditPostServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        switch (path) {
            case "/AddNovelController":
            case "/AddNovelController/edit":
                request.getRequestDispatcher("/addNovel.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // URL non valide
                break;
        }

        if (path.startsWith("/AddNovelController/edit/")) {
            String[] s = path.split("/");
            String id = s[s.length - 1];
//            customerDAO dao = new customerDAO();
//            customer obj = dao.getCustomer(id);
//            if (obj == null) {
//                response.sendRedirect("/customerController");
//            } else {
//                HttpSession session = request.getSession();
//                session.setAttribute("kh", obj);
//                request.getRequestDispatcher("/edit.jsp").forward(request, response);
//            }

        } else {
            if (path.startsWith("/AddNovelController/delete/")) {
                String[] s = path.split("/");

                String id = s[s.length - 1];
                editNovelDAO dao = new editNovelDAO();
                System.out.println(id);
                dao.deleteNovel(id);
            }
            response.sendRedirect("/AddNovelController");

        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("controllers.AddNovelController.doPost()");
        editNovelDAO dao = new editNovelDAO();
        if (request.getParameter("AddNew") != null) {
            String[] genre_id = request.getParameterValues("genre_ids");
            for (String string : genre_id) {
                System.out.println(string);
            }
            editNovelDAO daopedit = new editNovelDAO();
            String author = request.getParameter("author");
            String novel_title = request.getParameter("novel_title");
            String illustrator = request.getParameter("illustrator");
            String status = request.getParameter("status");
            String cover_img = "/imgs/" + request.getParameter("cover_img");
            String description = request.getParameter("description");
            int novelId = daopedit.getCurrentNovelId();
            System.out.println(novelId);
            
            if (cover_img.equals("/imgs/")) {
                cover_img += "logo.jpg";
            }
            novelInfor nvelifor = new novelInfor(novel_title, author, illustrator, status, cover_img, description);
            novelGenre nvelGen = new novelGenre(novelId, genre_id);
            dao.addNovel(nvelifor);
            dao.addNovelGenre(nvelGen);
            response.sendRedirect("/AddNovelController/edit");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
