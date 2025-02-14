/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.ChapterDAO;
import Model.Chapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author LENOVO
 */
public class editChapterController extends HttpServlet {

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
            out.println("<title>Servlet ChapterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChapterController at " + request.getContextPath() + "</h1>");
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
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length == 3 && pathParts[2].equals("CreateChapter")) {
            try {
                int novelId = Integer.parseInt(pathParts[1]);
                // Lưu novelId vào session
                request.getSession().setAttribute("novelId", novelId);
                request.getRequestDispatcher("/createChapter.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid novel ID");
            }
        } else {
            if (pathParts.length == 4 && pathParts[2].equals("EditChapter")) {
                try {
                    int novelId = Integer.parseInt(pathParts[1]);
                    int chapterId = Integer.parseInt(pathParts[3]);
                    // Lưu novelId vào session
                    request.getSession().setAttribute("novelId", novelId);
                    request.getSession().setAttribute("chapterId", chapterId);
                    request.getRequestDispatcher("/editChapter.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid novel ID");
                }
            }
            if (pathParts.length == 4 && pathParts[2].equals("DeleteChapter")) {
                try {
                    int novelId = Integer.parseInt(pathParts[1]);
                    int chapterId = Integer.parseInt(pathParts[3]);
                    ChapterDAO chapterDAO = new ChapterDAO();
                    chapterDAO.delete(chapterId);
                    response.sendRedirect("/editChapterController/" + novelId + "/CreateChapter"); // Ví dụ: chuyển hướng về trang danh sách chương
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid novel or chapter ID");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
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
        if (request.getParameter("btnCreateChapter") != null) {
            String chapter_title = request.getParameter("txtChapterTitle");
            String chapter_content = request.getParameter("txtChapterContent");
            String novel_id = request.getParameter("txtNovelID");
            Chapter obj = new Chapter(chapter_title, chapter_content, novel_id);
            ChapterDAO dao = new ChapterDAO();
            int count = dao.createChapter(obj);
            if (count > 0) {
                response.sendRedirect("/editChapterController/" + novel_id + "/CreateChapter");
            } else {
                response.sendRedirect("/editChapterController/" + novel_id + "/CreateChapter");
            }
        }
        if (request.getParameter("btnEditChapter") != null) {
            String chapter_id = request.getParameter("txtChapterID");
            String chapter_title = request.getParameter("txtChapterTitle");
            String chapter_content = request.getParameter("txtChapterContent");
            String novel_id = request.getParameter("txtNovelID");
            Chapter obj = new Chapter(chapter_id, chapter_title, chapter_content, novel_id);
            ChapterDAO dao = new ChapterDAO();
            int count = dao.editChapter(obj);
            if (count > 0) {
                request.getSession().setAttribute("editChapterSuccess", true);
                response.sendRedirect("/editChapterController/" + novel_id + "/EditChapter/" + chapter_id);
            } else {
                request.getSession().setAttribute("editChapterSuccess", false);
                response.sendRedirect("/editChapterController/" + novel_id + "/EditChapter/" + chapter_id);
            }
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
