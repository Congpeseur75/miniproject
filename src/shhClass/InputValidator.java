package shhClass;

import java.sql.*;

public class InputValidator {
    static Connection conn = DatabaseManager.getConnectDB();
    public static boolean validateMa(int ma){
        return ma > 0;
    }
    public static boolean validateDate(String dateStr){
        try {
            Date.valueOf(dateStr);
            return true;
        }
        catch (IllegalArgumentException e){
            return false;
        }
    }
    public static boolean validateTrangThai(String TrangThai){
        return TrangThai.equalsIgnoreCase("Đã duyệt") || TrangThai.equalsIgnoreCase("Chưa duyệt");
    }

    public static boolean kiemTraMaDKTonTai(int MaDK) throws SQLException {
        String sql = "SELECT 1 FROM DANGKYTRE WHERE MaDK = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, MaDK);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static boolean kiemTraMaPHTonTai(int MaPH) throws SQLException {
        String sql = "SELECT 1 FROM PHUHUYNH WHERE MaPH = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, MaPH);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static boolean kiemTraMaTreTonTai(int MaTre) throws SQLException {
        String sql = "SELECT 1 FROM TREEM WHERE MaTre = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, MaTre);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static boolean kiemTraMaLHTonTai(int MaLH) throws SQLException {
        String sql = "SELECT 1 FROM LOPHOC WHERE MaLH = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, MaLH);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }
}
