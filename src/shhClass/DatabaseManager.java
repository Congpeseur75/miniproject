package shhClass;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://DESKTOP-31VGQOU:1433;databaseName=QuanLyDichVuSinhHoatHe;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "123";
    private static Connection conn = null;

    // Method to establish connection
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(JDBC_DRIVER);
                System.out.println("Đang kết nối đến cơ sở dữ liệu...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Kết nối thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Method to close connection
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Đã đóng kết nối đến cơ sở dữ liệu.");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
