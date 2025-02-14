/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBconection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LEGION
 */
public class NotificationDAO {

    public NotificationDAO() {
    }

    public ResultSet getAllResultSetByUserID(int user_id) throws SQLException {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            String query = "select * from Notification where user_id=?";
            PreparedStatement pst = conn.prepareCall(query);
            pst.setInt(1, user_id);
            rs = pst.executeQuery();

        }
        return rs;
    }

    public ResultSet getAllNovelByID(int user_id) throws SQLException, SQLException, SQLException, SQLException {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                String query = "select n.*,noo.*\n"
                        + "from FavoriteMapping f\n"
                        + "join Novel n on f.novel_id = n.novel_id \n"
                        + "join Chapter c on n.novel_id = c.novel_id\n"
                        + "join  [Notification] noo on noo.user_id = f.user_id\n"
                        + "where f.user_id = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, user_id);
                rs = pst.executeQuery();

            } catch (SQLException ex) {
                Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public int updateReadStatus(String notification_id) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        int check = 0;
        if (conn != null) {
            try {
                String query = "update Notification\n"
                        + "set is_read = 1\n"
                        + "where notification_id = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, notification_id);
                check = pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
}
