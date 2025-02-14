package controllers;

import DAO.editNovelDAO;
import DAO.novelPageDAO;
import Model.Novel;
import Model.novelGenre;
import Model.novelInfor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

@WebServlet(urlPatterns = {"/ManagePostServlet", "/ManagePostServlet/*"})
public class ManagePostServlet extends HttpServlet {

    private editNovelDAO editNovelDAO;

    @Override
    public void init() throws ServletException {
        editNovelDAO = new editNovelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String path = request.getRequestURI();
        HttpSession session = request.getSession();
        novelPageDAO daoPage = new novelPageDAO();
        editNovelDAO daoEdit = new editNovelDAO();
        if (path.startsWith("/ManagePostServlet/")) {
            String[] s = path.split("/");
            String id = s[s.length - 1];
            novelInfor obj = new novelInfor();
            // tạo sesion để truyền id 
            session.setAttribute("novelId", id);
            ResultSet rs = daoPage.getNovelById(id);
            try {
                if (rs.next()) {
                    obj.setNovel_title(rs.getString("novel_title"));
                    obj.setAuthor(rs.getString("author"));
                    obj.setStatus(rs.getString("novel_status"));
                    obj.setCover_img(rs.getString("cover_img"));
                    obj.setDescription(rs.getString("description"));
                }
                session.setAttribute("obj", obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/updateNovel.jsp").forward(request, response);

        }

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "delete":
                deleteNovel(request, response);
                break;
            case "search":
                searchNovels(request, response);
                break;
            default:
                listNovels(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        editNovelDAO daoEdit = new editNovelDAO();
        HttpSession session = request.getSession();

        String id = (String) session.getAttribute("novelId");
        System.out.println(id);
        if (request.getParameter("btnUpdate") != null) {
            System.out.println("1234");
            String[] genre_id = request.getParameterValues("genre_ids");
            for (String string : genre_id) {
                System.out.println(string);
            }
            String author = request.getParameter("author");
            String novel_title = request.getParameter("novel_title");
            String illustrator = request.getParameter("illustrator");
            String status = request.getParameter("status");
            String cover_img = "/imgs/" + request.getParameter("cover_img");
            String description = request.getParameter("description");
            novelInfor nvelifor = new novelInfor(novel_title, author, illustrator, status, cover_img, description);
            novelGenre nvelGen = new novelGenre(Integer.parseInt(id), genre_id);
            daoEdit.updateNovel(nvelifor, id);
            daoEdit.addNovel(nvelifor);
            daoEdit.addNovelGenre(nvelGen);
            response.sendRedirect("/EditPostServlet/EditNovel/" + id);

        }

    }

    private void deleteNovel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        editNovelDAO.deleteNovel(id);
        response.sendRedirect(request.getContextPath() + "/ManageNovelsController");
    }

    protected void searchNovels(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        List<Novel> novels = editNovelDAO.getNovelsBySearchAndPage(query, currentPage);
        int totalPages = editNovelDAO.getTotalPages(query);
        request.setAttribute("listNovel", novels);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("query", query);
        request.getRequestDispatcher("ManageNovel.jsp").forward(request, response);
    }

    protected void listNovels(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }
        List<Novel> novels = editNovelDAO.getNovelsBySearchAndPage("", currentPage);
        int totalPages = editNovelDAO.getTotalPages("");
        request.setAttribute("listNovel", novels);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("ManageNovel.jsp").forward(request, response);
    }
}
