/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.novelPageDAO;
import Model.readingHistory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author LENOVO
 */
public class ReadingController extends HttpServlet {

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
            out.println("<title>Servlet ReadingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReadingController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        String path = request.getRequestURI();
        String user = (String) session.getAttribute("user");
        String userId = (String) session.getAttribute("userId");
        System.out.println(userId);
        novelPageDAO daoPage = new novelPageDAO();
        if (path != null && path.startsWith("/ReadingController/")) {
            String[] s = path.split("/");
            if (s.length > 0) {
                String chapterId = s[s.length - 1];
                String novelId = s[s.length - 2];
                session.setAttribute("chapterId", chapterId);
                session.setAttribute("novelId", novelId);
                readingHistory obj = new readingHistory();
                obj.setUser_id(userId);
                obj.setNovel_id(novelId);
                daoPage.updateView(Integer.parseInt(novelId));
                daoPage.addReadingHistory(obj);
                request.getRequestDispatcher("/reading.jsp").forward(request, response);
            } else {
                // Handle the case where the path does not have the expected structure
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path structure.");
            }
        } else {
            // Handle the case where the path does not start with "/ReadingController/"
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found.");
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
        processRequest(request, response);
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
