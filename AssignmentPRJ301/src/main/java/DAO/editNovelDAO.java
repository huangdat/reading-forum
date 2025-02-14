/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBconection;
import Model.Novel;
import Model.novelGenre;
import Model.novelInfor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static DB.DBconection.getConnection;

/**
 *
 * @author hp
 */
public class editNovelDAO {

    public int addNovel(novelInfor newInfor) {
        Connection conn = getConnection();
        String sql = "INSERT INTO [Novel] ( [novel_title], [author],  "
                + "[novel_status], [cover_img], [description], [view]) VALUES ( ?, ?, ?, ?, ?, ?)";
        int count = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newInfor.getNovel_title());
            pst.setString(2, newInfor.getAuthor());
            pst.setString(3, newInfor.getStatus());
            pst.setString(4, newInfor.getCover_img());
            pst.setString(5, newInfor.getDescription());
            pst.setInt(6, count);

            count = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace(); // <-- Print stack trace for debugging
        }
        return count;
    }

    // method to get novel information by id
    public novelInfor getNovelInfor(String id) {
        novelInfor novel = null;
        String sql = "SELECT * FROM Novel WHERE novel_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    novel = new novelInfor();
                    novel.setNovel_title(rs.getString("novel_title"));
                    novel.setAuthor(rs.getString("author"));
                    novel.setStatus(rs.getString("novel_status"));
                    novel.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return novel;
    }


    public int updateNovel(novelInfor novel, String id) {
        int result = 0;
        String sql = "UPDATE Novel SET novel_title = ?, author = ?, novel_status = ?, description = ? WHERE novel_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novel.getNovel_title());
            stmt.setString(2, novel.getAuthor());
            stmt.setString(3, novel.getStatus());
            stmt.setString(4, novel.getDescription());
            stmt.setString(5, id);
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    
    
    
    
    public ResultSet NovelGenre() {
        Connection conn = getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                String sql = (" SELECT  *\n"
                        + "  FROM Genre");

                PreparedStatement pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
        return rs;
    }

    public int addNovelGenre(novelGenre newGen) {
        Connection conn = getConnection();
        int count = 0;
        String[] genreIds = newGen.getGenre_id();
        for (Object gen : genreIds) {
            String sql = "INSERT INTO NovelGenre VALUES (?, ?)";
            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, Integer.toString(newGen.getNovel_id()));
                pst.setString(2, (String) gen);

                count = pst.executeUpdate();
            } catch (Exception e) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace(); // <-- Print stack trace for debugging
            }
        }
        return count;

    }

      public ResultSet NovelGenreByid(String novelId) {
        Connection conn = getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                String sql = ("SELECT g.genre_name,g.genre_id\n"
                        + "FROM dbo.NovelGenre ng\n"
                        + "JOIN dbo.Genre g ON ng.genre_id = g.genre_id\n"
                        + "WHERE ng.novel_id = ?;");
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, novelId);
                rs = pst.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }
    
      
      public int deleteNovelGenre(int novelId) {
    Connection conn = getConnection();
    String sql = "DELETE FROM NovelGenre WHERE novel_id=?";
    int count = 0;
    try {
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, novelId);
        count = pst.executeUpdate();
    } catch (Exception e) {
        Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
        e.printStackTrace();
    }
    return count;
}
      
      
    
    public int getCurrentNovelId() {
        Connection conn = getConnection();
        int novelId = -1; // Sử dụng -1 như giá trị mặc định để chỉ ra lỗi hoặc không có kết quả

        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT TOP 1 novel_id FROM Novel ORDER BY created_at DESC");

                if (rs.next()) {
                    novelId = rs.getInt("novel_id");
                }
            } catch (SQLException ex) {
                Logger.getLogger(editNovelDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return novelId + 1;
    }
    //    
    //    
    //      public customer getCustomer(String id) {
    //        Connection conn = DBconection.getConnection();
    //                    customer obj;
    //
    //        try {
    //          String sql = "SELECT * FROM nguoidung WHERE id=?";
    //            PreparedStatement pst = conn.prepareStatement(sql);
    //            pst.setString(1, id);
    //            ResultSet rs = pst.executeQuery();
    //           
    //            if (rs.next()) {
    //                 obj = new customer();
    //                obj.setId(rs.getString("id"));
    //                obj.setFullName(rs.getString("fullname"));
    //                obj.setAddress(rs.getString("address"));
    //            } else{
    //                obj =null;
    //            }
    //
    //        }
    //        catch (Exception e) {
    //obj =null;
    //        }
    //        return obj ;
    //    }
    // public int updateCustomer(String id , customer newinfo){
    //     Connection conn = DBconection.getConnection();
    //     int count;
    //     try {
    //         String sql = "Update nguoidung set fullname = ?, address = ? where id = ?";
    //         PreparedStatement pst = conn.prepareStatement(sql);
    //         
    //         pst.setString(1, newinfo.getFullName());
    //         pst.setString(2, newinfo.getAddress());
    //         pst.setString(3, id);
    //         count=pst.executeUpdate();
    //     } catch (Exception e) {
    //         count=0;
    //     }
    //     return count;
    // }

    public int deleteNovel(String id) {
        Connection conn = getConnection();
        int count;
        try {
            String sql = "delete from Novel where id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            count = pst.executeUpdate();
        } catch (Exception e) {
            count = 0;
        }
        return count;
    }
    
    
    
    
    
    
    
    
    
    
        public List<Novel> getAllNovels() {
        List<Novel> novels = new ArrayList<>();
        String sql = "SELECT * FROM Novel ORDER BY novel_id DESC";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Novel novel = new Novel(
                        rs.getInt("novel_id"),
                        rs.getString("novel_title"),
                        rs.getString("author"),
                        rs.getString("novel_status"),
                        rs.getString("cover_img"),
                        rs.getString("description"),
                        rs.getInt("view"),
                        rs.getTimestamp("created_at") // Ensure correct data type
                );
                novels.add(novel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return novels;
    }

    public List<Novel> searchNovelsByTitle(String title) {
        List<Novel> novels = new ArrayList<>();
        String query = "SELECT * FROM Novel WHERE novel_title LIKE ? ORDER BY novel_id DESC";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + title + "%");

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    novels.add(new Novel(
                            rs.getInt("novel_id"),
                            rs.getString("novel_title"),
                            rs.getString("author"),
                            rs.getString("novel_status"),
                            rs.getString("cover_img"),
                            rs.getString("description"),
                            rs.getInt("view"),
                            rs.getTimestamp("created_at") // Ensure correct data type
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return novels;
    }

    public List<Novel> getNovelsBySearchAndPage(String search, int page) {
        List<Novel> novels = new ArrayList<>();
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        String sql = "SELECT * FROM Novel WHERE novel_title LIKE ? ORDER BY novel_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Novel novel = new Novel(
                            rs.getInt("novel_id"),
                            rs.getString("novel_title"),
                            rs.getString("author"),
                            rs.getString("novel_status"),
                            rs.getString("cover_img"),
                            rs.getString("description"),
                            rs.getInt("view"),
                            rs.getTimestamp("created_at") // Ensure correct data type
                    );
                    novels.add(novel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return novels;
    }

    public int getTotalPages(String search) {
        int totalNovels = 0;
        int pageSize = 10;

        String sql = "SELECT COUNT(*) AS total FROM Novel WHERE novel_title LIKE ?";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalNovels = rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) Math.ceil((double) totalNovels / pageSize);
    }

    public void deleteNovel(int id) {
        String sql = "DELETE FROM Novel WHERE novel_id = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    

}
