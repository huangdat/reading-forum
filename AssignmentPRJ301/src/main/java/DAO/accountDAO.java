/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBconection;
import Model.novelInfor;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hp
 */
public class accountDAO {

    public ResultSet listFav(String user_id) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                String sql = ("SELECT \n"
                        + "    n.novel_id,\n"
                        + "    n.novel_title,\n"
                        + "    n.cover_img\n"
                        + "FROM \n"
                        + "    [Novel] AS n\n"
                        + "JOIN \n"
                        + "    FavoriteMapping AS f ON n.novel_id = f.novel_id\n"
                        + "WHERE \n"
                        + "    f.user_id = ?;");
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, user_id);
                rs = pst.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

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


    public int updateNovel(novelInfor novel, String novelId) {
        Connection conn = DBconection.getConnection();
        String sql = "UPDATE Novel SET novel_title=?, author=?, novel_status=?, cover_img=?, description=? WHERE novel_id=?";
        int count = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, novel.getNovel_title());
            pst.setString(2, novel.getAuthor());
            pst.setString(3, novel.getStatus());
            pst.setString(4, novel.getCover_img());
            pst.setString(5, novel.getDescription());
            pst.setString(6, novelId);
            count = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return count;
    }


}
