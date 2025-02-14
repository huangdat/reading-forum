 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author hp
 */

 import DB.DBconection;
 import Model.Register;
 import Model.User;
 import Model.login;

 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;

 public class LoginDAO {
     private Connection conn = null;

     public LoginDAO() {
         conn = DBconection.getConnection();
     }

//    public String getMD5Hash(String pass) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] digest = md.digest(pass.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : digest) {
//                hexString.append(String.format("%02x", b));
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    public boolean isUser(User user) {
        try {
            String sql = "select role from [User] where username=? and password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("user")) {
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAdmin(User user) {
        try {
            String sql = "select role from [User] where username=? and password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("admin")) {
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkRegister(Register lo) {
        Connection conn = DBconection.getConnection();
        try {
            String sql = "SELECT password FROM [User] WHERE username=? or email =?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, lo.getUser());
            pst.setString(2, lo.getEmail());
            ResultSet rs = pst.executeQuery();
            System.out.println("DAO.LoginDAO.checkRegister()");

            if (rs.next()) {
                
                System.out.println("DAO.LoginDAO.checkRegister()");
                return false;
            } else {
                System.out.println("DAO.LoginDAO.checkRegister()");

                return true;
            }
        } catch (Exception e) {
            System.out.println("DAO.LoginDAO.checkRegister()");
            e.printStackTrace();
            return false;
        }
    }

    public boolean register(Register lo) {
        Connection conn = DBconection.getConnection();
        String user = "user";
        String avt = "/imgs/avt.jpg";
        try {
            System.out.println("DAO.LoginDAO.register()");
            String sql = "INSERT INTO [User] (email,username, password, role, avatar) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, lo.getEmail());
            pst.setString(2, lo.getUser());
            pst.setString(3, lo.getPass());
            pst.setString(4, user);
            pst.setString(5, avt);

            int rs = pst.executeUpdate();
            return rs > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public String getUsername(login lg) {
        Connection conn = DBconection.getConnection();
        ResultSet rs = null;
        String username = null;
        try {
            String sql = "select username from [User] where email=? and password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, lg.getEmail());
            pst.setString(2, lg.getPassword());
            rs = pst.executeQuery();
            if (rs.next()) {
                username = String.valueOf(rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

}
