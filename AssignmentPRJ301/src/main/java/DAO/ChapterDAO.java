/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBconection;
import Model.Chapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ChapterDAO {

    public int createChapter(Chapter obj) {
        Connection conn = DBconection.getConnection();
        String sql = "insert into Chapter values(?,?,?)";
        int count = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, obj.getChapter_title());
            pst.setString(2, obj.getChapter_content());
            pst.setString(3, obj.getNovel_id());
            count = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ChapterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int editChapter(Chapter obj) {
        Connection conn = DBconection.getConnection();
        String sql = "UPDATE Chapter SET chapter_title = ?, chapter_content = ?, novel_id = ? WHERE chapter_id = ?";
        int count = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, obj.getChapter_title());
            pst.setString(2, obj.getChapter_content());
            pst.setString(3, obj.getNovel_id());
            pst.setString(4, obj.getChapter_id());
            count = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ChapterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int delete(int id) {
        Connection conn = DBconection.getConnection();
        int count = 0;
        try {
            String sql = "delete from Chapter where  chapter_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            count = pst.executeUpdate();
        } catch (Exception e) {
            count = 0;
        }
        return count;
    }
}
