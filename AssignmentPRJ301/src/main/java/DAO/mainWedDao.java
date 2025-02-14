/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBconection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class mainWedDao {

    public ResultSet getNewUpdate() {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                rs = st.executeQuery("SELECT * \n"
                        + "FROM Novel \n"
                        + "ORDER BY created_at DESC");
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public ResultSet getAll() {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                rs = st.executeQuery("SELECT top 5 * \n"
                        + "FROM Novel\n"
                        + "ORDER BY [view] DESC");
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public ResultSet getNewNovel() {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                rs = st.executeQuery("SELECT * \n"
                        + "FROM Novel \n"
                        + "ORDER BY created_at DESC");
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public ResultSet readingHistory() {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                rs = st.executeQuery("SELECT n.cover_img,n.novel_title,n.novel_id\n" +
"                        FROM ReadingHistory r JOIN Novel n  \n" +
"                        ON r.novel_id = n.novel_id\n" +
"                        ORDER BY r.history_id DESC;");

            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public ResultSet favourite() {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        if (conn != null) {
            try {
                Statement st = conn.createStatement();
                rs = st.executeQuery("  SELECT *\n"
                        + "FROM [NovelOne].[dbo].[Novel]\n"
                        + "WHERE novel_id IN (\n"
                        + "    SELECT TOP 5 WITH TIES novel_id\n"
                        + "    FROM [NovelOne].[dbo].[FavoriteMapping]\n"
                        + "    GROUP BY novel_id\n"
                        + "    ORDER BY COUNT(*) DESC \n"
                        + ");");

            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public ResultSet NovelGenre(String novelId) {
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

}
