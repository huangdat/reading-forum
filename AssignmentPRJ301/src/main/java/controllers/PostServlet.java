package controllers;

import java.io.*;
import java.sql.ResultSet;

import DAO.novelPageDAO;
import Model.Rating;
import Model.comment;
import Model.favorite;
import Model.readingHistory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "PostServlet", value = "/PostServlet")
public class PostServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PostServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        String userId = (String) session.getAttribute("userId");
        System.out.println(userId);
        novelPageDAO daoPage = new novelPageDAO();

        if (path.equals("/PostSevlet")) {
            request.getRequestDispatcher("/mainWed.jsp").forward(request, response);
        } else {
            if (path.equals("/PostServlet/novelpage")) {
                request.getRequestDispatcher("/novelPage.jsp").forward(request, response);
            } else {

                // khi bâm vào novel
                if (path.startsWith("/PostServlet/novel_id/")) {
                    String[] s = path.split("/");
                    String id = s[s.length - 1];
                    // tạo sesion để truyền id
                    session.setAttribute("id", id);
                    favorite fav = new favorite();
                    fav.setNovel_id(Integer.parseInt(id));
                    fav.setUserId(userId);
                    session.setAttribute("AvgRating", daoPage.getAvgRating(id));
                    if (session.getAttribute("AvgRating") == null) {
                        session.setAttribute("AvgRating", "0");

                    }
                    session.setAttribute("TotalCmt", daoPage.getTotalCmt(id));

                    if (fav != null) {
                        session.setAttribute("favorite", fav);
                        System.out.println(session.getAttribute("id"));
                        // check user có thích hay không
                        if (daoPage.checkUserClickFollow(fav)) {
                            session.setAttribute("follow", "đã thích");
                            session.setAttribute("favoriteCount", daoPage.getFavoriteResult(id));
                            System.out.println(session.getAttribute("favoriteCount") + "--123");
                            session.setAttribute("favoriteCount", daoPage.getFavoriteResult(id));
                        } else {
                            session.setAttribute("favoriteCount", daoPage.getFavoriteResult(id));
                            session.setAttribute("follow", "thích");
                            session.setAttribute("favoriteCount", daoPage.getFavoriteResult(id));
                        }

                        request.getRequestDispatcher("/novelPage.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/novelPage.jsp").forward(request, response);
                    }

                    // check khi bấm like
                } else if (path.startsWith("/PostServlet/favorite/")) {
                    String[] s = path.split("/");
                    String id = s[s.length - 1];
                    System.out.println(id);
                    System.out.println(user);
                    favorite fav = new favorite();
                    fav.setNovel_id(Integer.parseInt(id));
                    fav.setUserId(userId);
                    if (fav != null) {
                        daoPage.addFavoriteNovel(fav);
                        if (daoPage.checkUserClickFollow(fav)) {
                            session.setAttribute("follow", "đã thích");
//                            daoPage.updateFavouriteNovel(Integer.parseInt(id), 1);
                            session.setAttribute("favoriteCount", daoPage.getFavoriteResult(id));
                        } else {
                            session.setAttribute("follow", "thích");
//                            daoPage.updateFavouriteNovel(Integer.parseInt(id), 0);
                            session.setAttribute("favoriteCount", daoPage.getFavoriteResult(id));
                        }
                        request.getRequestDispatcher("/novelPage.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/novelPage.jsp").forward(request, response);
                    }

                    // xử lí khi bấm vào bình luận
                } else if (path.startsWith("/PostServlet/comment/")) {
                    String[] s = path.split("/");
                    String id = s[s.length - 1];
                    System.out.println(id + "cmt");
                    ResultSet rs = daoPage.getUser(user);
                    System.out.println(user + "cmt");
                    session.setAttribute("AvgRating", daoPage.getAvgRating(id));
                    if (session.getAttribute("AvgRating") == null) {
                        session.setAttribute("AvgRating", "0");

                    }
                    session.setAttribute("TotalCmt", daoPage.getTotalCmt(id));
                    try {
                        while (rs.next()) {
                            System.out.println("cmt");
                            comment cmt = new comment();
                            cmt.setUserID(userId);
                            cmt.setNovel_id(id);
                            cmt.setAvatar(rs.getString("avatar"));
                            cmt.setContent(request.getParameter("cmt"));
                            daoPage.addCommentUser(cmt);
                            response.sendRedirect("/PostServlet/novel_id/"+id);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (path.startsWith("/PostServlet/rating/")) {
                    String[] s = path.split("/");
                    String id = s[s.length - 1];
                    System.out.println(id + "rating");
                    ResultSet rs = daoPage.getUser(user);
                    session.setAttribute("AvgRating", daoPage.getAvgRating(id));
                    if (session.getAttribute("AvgRating") == null) {
                        session.setAttribute("AvgRating", "0");

                    }
                    session.setAttribute("TotalCmt", daoPage.getTotalCmt(id));
                    try {
                        while (rs.next()) {
                            System.out.println("cmt");
                            Rating rat = new Rating();
                            rat.setUserId(userId);
                            rat.setNovel_id(id);
                            rat.setContent(request.getParameter("comment_rating"));
                            rat.setRating_number(request.getParameter("rating"));
                            daoPage.addRatingUser(rat);
                            response.sendRedirect("/PostServlet/novel_id/"+id);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (path.startsWith("/PostServlet/chapter/")) {
                    String[] s = path.split("/");
                    String NovelId = s[s.length - 1];
                    readingHistory obj = new readingHistory();
                    obj.setUser_id(userId);
                    obj.setNovel_id(NovelId);
                    daoPage.updateView(Integer.parseInt(NovelId));
                    daoPage.addReadingHistory(obj);
                    request.getRequestDispatcher("/novelPage.jsp").forward(request, response);

                }

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