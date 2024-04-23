package shhClass;

import java.util.Date;

public class DangKyTre {
    private int MaPH;
    private int MaTre;
    private int MaLH;
    private Date NgayDangKy;
    private String TrangThai = "Chưa Duyệt";

    public DangKyTre() {
    }

    public DangKyTre(int maPH, int maTre, int maLH, Date ngayDangKy, String trangThai) {
        MaPH = maPH;
        MaTre = maTre;
        MaLH = maLH;
        NgayDangKy = ngayDangKy;
        TrangThai = trangThai;
    }

    public int getMaPH() {
        return MaPH;
    }

    public void setMaPH(int maPH) {
        MaPH = maPH;
    }

    public int getMaTre() {
        return MaTre;
    }

    public void setMaTre(int maTre) {
        MaTre = maTre;
    }

    public int getMaLH() {
        return MaLH;
    }

    public void setMaLH(int maLH) {
        MaLH = maLH;
    }

    public java.sql.Date getNgayDangKy() {
        return (java.sql.Date) NgayDangKy;
    }

    public void setNgayDangKy(Date ngayDangKy) {
        NgayDangKy = ngayDangKy;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
