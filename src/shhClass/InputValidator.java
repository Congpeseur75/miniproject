package shhClass;

import java.sql.Date;

public class InputValidator {
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
}
