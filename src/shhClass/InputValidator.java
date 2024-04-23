package shhClass;

import java.sql.*;

public class InputValidator {
    static Connection conn = DatabaseManager.getConnection();
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
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, MaDK);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean kiemTraMaPHTonTai(int MaPH) throws SQLException {
        String sql = "SELECT 1 FROM PHUHUYNH WHERE MaPH = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, MaPH);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean kiemTraMaTreTonTai(int MaTre) throws SQLException {
        String sql = "SELECT 1 FROM TREEM WHERE MaTre = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, MaTre);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean kiemTraMaLHTonTai(int MaLH) throws SQLException {
        String sql = "SELECT 1 FROM LOPHOC WHERE MaLH = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, MaLH);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
