package DAO;

import DB.DBconection;
import Model.CommentDelete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public List<CommentDelete> getAllComments() {
        List<CommentDelete> comments = new ArrayList<>();
        String sql = "SELECT c.comment_id, c.comment_content, c.user_id, c.novel_id, c.chapter_id, u.username, u.avatar "
                + "FROM Comment c "
                + "JOIN [User] u ON c.user_id = u.user_id "
                + "ORDER BY c.comment_id DESC";

        try ( Connection conn = DBconection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CommentDelete comment = new CommentDelete(
                        rs.getInt("comment_id"),
                        rs.getString("comment_content"),
                        rs.getInt("user_id"),
                        rs.getInt("novel_id"),
                        rs.getObject("chapter_id") != null ? rs.getInt("chapter_id") : null,
                        rs.getString("username"),
                        rs.getString("avatar")
                );
                comments.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    public void deleteComment(int id) {
        String sql = "DELETE FROM Comment WHERE comment_id = ?";
        try ( Connection conn = DBconection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CommentDelete> getCommentsBySearchAndPage(String search, int page) {
        List<CommentDelete> comments = new ArrayList<>();
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        String sql = "SELECT c.comment_id, c.comment_content, c.user_id, c.novel_id, c.chapter_id, u.username, u.avatar "
                + "FROM Comment c "
                + "JOIN [User] u ON c.user_id = u.user_id "
                + "WHERE c.comment_content LIKE ? "
                + "ORDER BY c.comment_id DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try ( Connection conn = DBconection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CommentDelete comment = new CommentDelete(
                            rs.getInt("comment_id"),
                            rs.getString("comment_content"),
                            rs.getInt("user_id"),
                            rs.getInt("novel_id"),
                            rs.getObject("chapter_id") != null ? rs.getInt("chapter_id") : null,
                            rs.getString("username"),
                            rs.getString("avatar")
                    );
                    comments.add(comment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    public int getTotalPages(String search) {
        int totalComments = 0;
        int pageSize = 10;

        String sql = "SELECT COUNT(*) AS total "
                + "FROM Comment c "
                + "JOIN [User] u ON c.user_id = u.user_id "
                + "WHERE c.comment_content LIKE ?";

        try ( Connection conn = DBconection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalComments = rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int) Math.ceil((double) totalComments / pageSize);
    }

}
