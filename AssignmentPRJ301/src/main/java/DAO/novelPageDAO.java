/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBconection;
import Model.Rating;
import Model.comment;
import Model.favorite;
import Model.readingHistory;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class novelPageDAO {
    
    public ResultSet getNovelById(String novelId) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                String sql = (" SELECT *\n"
                        + " FROM Novel where novel_id=?");
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, novelId);
                rs = pst.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }
    
    public ResultSet getNovelGenre(String novelId) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                String sql = ("SELECT g.genre_name\n"
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
    
    public int addReadingHistory(readingHistory obj) {
        Connection conn = DBconection.getConnection();
        int count;
        try {
            String sql = "  INSERT INTO [ReadingHistory] ([user_id], novel_id)\n"
                    + "                    VALUES (?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, obj.getUser_id());
            pst.setString(2, obj.getNovel_id());
            
            count = pst.executeUpdate();
        } catch (Exception e) {
            count = 0;
        }
        return count;
    }
    
    public ResultSet getChapterById(String novelId) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                String sql = ("   SELECT *\n"
                        + "  FROM Chapter where novel_id=?");
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, novelId);
                rs = pst.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

// người đọc yêu thích truyện
    public int addFavoriteNovel(favorite fav) {
        Connection conn = DBconection.getConnection();
        int count = 0;
        System.out.println("DAO.novelPageDAO.addFavoriteNovel()");

        // Kiểm tra trùng lặp trước khi thêm vào database
        try ( PreparedStatement checkPst = conn.prepareStatement("SELECT COUNT(*) FROm [FavoriteMapping]\n"
                + "WHERE user_id = ? AND novel_id = ?")) {
            checkPst.setString(1, fav.getUserId());
            checkPst.setInt(2, fav.getNovel_id());
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Đã thích rồi, xóa bản ghi cũ
                String sql = "DELETE FROM [FavoriteMapping] WHERE user_id = ? AND novel_id = ?";
                try ( PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, fav.getUserId());
                    pst.setInt(2, fav.getNovel_id());
                    count = pst.executeUpdate();
                    System.out.println("User đã thích truyện này rồi. Đã xóa bản ghi cũ và cập nhật.");
                    return count; // Trả về số lượng bản ghi đã xóa
                } catch (Exception e) {
                    Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
                    e.printStackTrace();
                }
            } else {

                // Chưa thích, thêm vào database
                String sql = "INSERT INTO [FavoriteMapping] ( user_id,[novel_id]) VALUES (?, ?)";
                try ( PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, fav.getUserId());
                    pst.setInt(2, fav.getNovel_id());
                    count = pst.executeUpdate();
                } catch (Exception e) {
                    Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        
        return count;
    }
// người đọc yêu thích truyện

    public boolean checkUserClickFollow(favorite fav) {
        Connection conn = DBconection.getConnection();
        int count = 0;
        System.out.println("DAO.novelPageDAO.addFavoriteNovel()");
        // Kiểm tra trùng lặp trước khi thêm vào database
        try ( PreparedStatement checkPst = conn.prepareStatement("SELECT COUNT(*) FROM [FavoriteMapping]\n"
                + "               WHERE user_id = ? AND novel_id = ?")) {
            checkPst.setString(1, fav.getUserId());
            checkPst.setInt(2, fav.getNovel_id());
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
    }
    
    public String getFavoriteResult(String novelId) {
        Connection conn = DBconection.getConnection();
        String favoriteCount = "";
        if (conn != null) {
            try ( PreparedStatement pst = conn.prepareStatement(" SELECT novel_id, COUNT(*) AS favorite_count\n"
                    + "FROM FavoriteMapping\n"
                    + "WHERE novel_id = ?\n"
                    + "GROUP BY novel_id;")) {
                pst.setString(1, novelId);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    favoriteCount = String.valueOf(rs.getInt("favorite_count"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return favoriteCount;
    }
    
    public String getTotalCmt(String novelId) {
        Connection conn = DBconection.getConnection();
        String favoriteCount = "";
        if (conn != null) {
            try ( PreparedStatement pst = conn.prepareStatement(" SELECT COUNT(*) as totalCmt\n"
                    + "                    FROM Comment\n"
                    + "                    WHERE novel_id = ?")) {
                pst.setString(1, novelId);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    favoriteCount = String.valueOf(rs.getInt("totalCmt"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return favoriteCount;
    }
    
    public String getAvgRating(String novelId) {
        Connection conn = DBconection.getConnection();
        String averageRating = "";
        if (conn != null) {
            try ( PreparedStatement pst = conn.prepareStatement(" SELECT STR(ROUND(AVG(CAST(r.rating_number AS DECIMAL(10,2))), 1), 4, 1) AS average_rating\n"
                    + "FROM [dbo].[Rating] r\n"
                    + "WHERE novel_id = ?")) {
                pst.setString(1, novelId);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    averageRating = rs.getString("average_rating");                    
                } else if (averageRating == null) {
                    averageRating = "0";
                }
               
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return averageRating;
    }

//    public int updateFavouriteNovel(int novelId, int like) {
//        Connection conn = DBconection.getConnection();
//        int updatedFavouriteCount = 0;
//        if (like == 1) {
//            try ( PreparedStatement stmt = conn.prepareStatement("UPDATE novel SET favourite = favourite + ? WHERE novel_id = ?")) {
//                stmt.setInt(1, 1);//+1
//                stmt.setInt(2, novelId);
//                updatedFavouriteCount = stmt.executeUpdate();
//            } catch (SQLException ex) {
//                Logger.getLogger(novelPageDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return updatedFavouriteCount;
//        } else {
//            try ( PreparedStatement stmt = conn.prepareStatement("UPDATE novel SET favourite = favourite - ? WHERE novel_id = ?")) {
//                stmt.setInt(1, 1);  // -1 
//                stmt.setInt(2, novelId);
//                updatedFavouriteCount = stmt.executeUpdate();
//                return updatedFavouriteCount;
//
//            } catch (SQLException ex) {
//                Logger.getLogger(novelPageDAO.class.getName()).log(Level.SEVERE, null, ex);
//                return updatedFavouriteCount;
//
//            }
//        }
//
//    }
    public ResultSet getUser(String user) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                String sql = "SELECT * FROM [User] WHERE username = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, user);
                rs = pst.executeQuery();
                
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    // add comment into table comment 
    public int addCommentUser(comment cmt) {
        Connection conn = DBconection.getConnection();
        int count;
        try {
            String sql = "INSERT INTO [Comment] ([comment_content], [user_id], [novel_id])\n"
                    + "                    VALUES (?, ?, ?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, cmt.getContent());
            pst.setString(2, cmt.getUserID());
            pst.setString(3, cmt.getNovel_id());
            count = pst.executeUpdate();
        } catch (Exception ex) {
            count = 0;
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            ex.printStackTrace();
        }
        return count;
    }
    
    public ResultSet getCmtNovel(String novelId) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                String sql = (" SELECT u.username, u.avatar, c.comment_content\n"
                        + "FROM [User] u\n"
                        + "JOIN [Comment] c ON u.user_id = c.user_id\n"
                        + "WHERE c.novel_id =?;");
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, novelId);
                rs = pst.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }
    
    public ResultSet getRatingNovel(String novelId) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                String sql = ("   SELECT   [U].avatar, \n"
                        + "                  [R].rating_content, \n"
                        + "                  [U].username, \n"
                        + "                  [R].rating_number\n"
                        + "FROM   [NovelOne].[dbo].[Rating] AS [R]\n"
                        + "       JOIN [NovelOne].[dbo].[User] AS [U] ON [R].user_id = [U].user_id \n"
                        + "WHERE [R].novel_id = ?");
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, novelId);
                rs = pst.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }
    
    public int addRatingUser(Rating rat) {
        Connection conn = DBconection.getConnection();
        int count;
        try {
            String sql = "/*addRatingNovel*/\n"
                    + "INSERT INTO [Rating] (novel_id, user_id, [rating_content], rating_number)\n"
                    + "                    VALUES (?, ?, ?, ?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, rat.getNovel_id());
            pst.setString(2, rat.getUserId());
            pst.setString(3, rat.getContent());
            pst.setString(4, rat.getRating_number());
            count = pst.executeUpdate();
        } catch (Exception ex) {
            count = 0;
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            ex.printStackTrace();
        }
        return count;
    }
    
    public int updateView(int novelId) {
        Connection conn = DBconection.getConnection();
        int count = 0;
        try ( PreparedStatement stmt = conn.prepareStatement("  UPDATE Novel\n"
                + "SET [view] = [view] + ?\n"
                + "WHERE novel_id = ?")) {
            stmt.setInt(1, 1);//+1
            stmt.setInt(2, novelId);
            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(novelPageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
        
    }
    
}
