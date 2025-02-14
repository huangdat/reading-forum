package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBconection {
    public static Connection conn = null;
    public static Connection getConnection() {

        try {
            // Bước 1: Khai báo driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Bước 2: Khai báo thông tin server
            String url = "jdbc:sqlserver://LAPTOP-RB67CJ7B:1433;databaseName=NovelOne;" +
                    "user=sa;password=12345;encrypt=true;trustServerCertificate=true;";
            // Bước 3: Tạo doi tuong kết nối
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DBconection.class.getName()).severe(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(DBconection.class.getName()).severe(e.getMessage());
        }
    }
}
