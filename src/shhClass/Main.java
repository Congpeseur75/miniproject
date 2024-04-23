package shhClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static shhClass.InputValidator.*;

public class Main {
    static ArrayList<DangKyTre> dangKyTres = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static Connection conn = DatabaseManager.getConnectDB();


    public static void main(String[] args) {
        DatabaseManager.getConnectDB();
        boolean exit = false;
        while (!exit){
            try {
                System.out.println("\n==================MENU==================");
                System.out.println("|       1.thêm thông tin đăng ký       |");
                System.out.println("|       2.hiển thị toàn bộ thông tin   |");
                System.out.println("|       3.Sửa thông tin đăng ký        |");
                System.out.println("|       4.Xóa thông tin đăng ký        |");
                System.out.println("|       5.Tìm kiếm thông tin đăng ký   |");
                System.out.println("|       6.Liệt kê thời gian học        |");
                System.out.println("========================================");
                System.out.println("Chọn chức năng: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:
                        themThongTinDangKy();
                        break;
                    case 2:
                        hienThiToanBoThongTin();
                        break;
                    case 3:
                        suaThongTinDangKyDatabase();
                        break;
                    case 4:
                        xoaThongTinDangKyDatabase();
                        break;
                    case 5:
                        timKiemThongTinDangKyDatabase();
                        break;
                    case 6:
                        lietKeThoiGianHoc();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("lựa chọn không hợp lệ vui lòng chọn lại");
                }
            }
            catch (NumberFormatException e){
                System.out.println("lỗi " + e);
            }
            catch (Exception e){
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        }
        DatabaseManager.closeConnectDB();
    }
    public static void themThongTinDangKy(){
        System.out.println("\n==========Thêm thông tin đăng ký==========");
        boolean isValidInput = false;
        do {
            try {
                System.out.println("Nhập mã phụ huynh: ");
                int MaPH = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaPH)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }
                if (!kiemTraMaPHTonTai(MaPH)) {
                    System.out.println("Mã Phụ huynh không tồn tại. Vui lòng nhập lại.");
                    continue;
                }


                System.out.println("Nhập mã trẻ em: ");
                int MaTre = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaTre)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }
                if (!kiemTraMaTreTonTai(MaTre)){
                    System.out.println("Mã trẻ em không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("Nhập mã lớp: ");
                int MaLH = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaLH)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }
                if (!kiemTraMaLHTonTai(MaLH)){
                    System.out.println("Mã lớp học không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("Nhập ngày đăng ký (yyyy-MM-dd):");
                String ngayDangKyStr = sc.nextLine();
                if (!InputValidator.validateDate(ngayDangKyStr)) {
                    System.out.println("Ngày đăng ký không hợp lệ.");
                    continue;
                }

                System.out.println("Nhập trạng thái đăng ký (ấn Enter để bỏ qua và sử dụng trạng thái mặc định 'Chưa Duyệt'):");
                String trangThaiInput = sc.nextLine();
                String trangThai = trangThaiInput.isEmpty() ? "Chưa Duyệt" : trangThaiInput;
                if (!InputValidator.validateTrangThai(trangThai)) {
                    System.out.println("Trạng thái đăng ký không hợp lệ.");
                    continue;
                }

                DangKyTre dangKyTre = new DangKyTre(MaPH, MaTre, MaLH, Date.valueOf(ngayDangKyStr), trangThai);
                dangKyTres.add(dangKyTre);
                themDangKyHocSinhVaoDatabase(dangKyTre);

                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: ");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } while (!isValidInput);
    }
    public static void themDangKyHocSinhVaoDatabase(DangKyTre dangKyTre){
        try {
            String sql = "INSERT INTO DANGKYTRE(MaPH,MaTre,MaLH,NgayDangKy,TrangThai) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, dangKyTre.getMaLH());
            pst.setInt(2,dangKyTre.getMaTre());
            pst.setInt(3,dangKyTre.getMaLH());
            pst.setDate(4,dangKyTre.getNgayDangKy());
            pst.setString(5,dangKyTre.getTrangThai());
            pst.executeUpdate();
            System.out.println("đã thêm thông tin đăng ký thành công");

        } catch (SQLException e) {
            System.out.println("vui lòng nhập lại");
        }
    }
    public static void hienThiToanBoThongTin(){
        System.out.println("\n==========Hiển thị toàn bộ thông tin============");
        boolean isValidInput = false;
        do {
            try {
                System.out.println("Nhập MaDK cần tìm: ");
                int MaDK = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaDK)) {
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                if (!kiemTraMaDKTonTai(MaDK)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }
                System.out.println("+----------+----------+------------------+---------------------+-------------------------+--------------------------+----------+------------------+----------------+----------+----------+--------------+-----------+-------------------+------------+--------------+--------------+");
                System.out.println("|   MaDK   |   MaPH   |     HoTenPH      |        DiaChi       |          SoDT           |            Email         |  MaTre   |     HoTenTre     |     NgaySinh   | GioiTinh |  MaLH    |   PhongHoc   |   SoBuoi  |    NgayKhaiGiang  |   HocPhi   |  NgayDangKy  |   TrangThai  |");
                System.out.println("+----------+----------+------------------+---------------------+-------------------------+--------------------------+----------+------------------+----------------+----------+----------+--------------+-----------+-------------------+------------+--------------+--------------+");


                String sql = "SELECT DK.MaDK, PH.MaPH, PH.HoTenPH, PH.DiaChi, PH.SoDT, PH.Email, T.MaTre, T.HoTenTre, T.NgaySinh, T.GioiTinh, LH.MaLH, LH.PhongHoc, LH.SoBuoi, LH.NgayKhaiGiang, LH.HocPhi, DK.NgayDangKy, DK.TrangThai\n" +
                        "FROM DANGKYTRE DK\n" +
                        "JOIN PHUHUYNH PH ON DK.MaPH = PH.MaPH\n" +
                        "JOIN TREEM T ON DK.MaTre = T.MaTre\n" +
                        "JOIN LOPHOC LH ON DK.MaLH = LH.MaLH\n" +
                        "WHERE DK.MaDK=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, MaDK);
                ResultSet rs = pst.executeQuery();
                if (rs.next()){
                    System.out.printf("| %-8s | %-8s | %-16s | %-19s | %-23s | %-23s | %-8s | %-16s | %-14s | %-8s | %-8s | %-12s | %-9s | %-17s | %-10s | %-12s | %-12s |\n",
                        rs.getInt("MaDK"),
                        rs.getInt("MaPH"),
                        rs.getString("HoTenPH"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDT"),
                        rs.getString("Email"),
                        rs.getInt("MaTre"),
                        rs.getString("HoTenTre"),
                        rs.getDate("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getInt("MaLH"),
                        rs.getString("PhongHoc"),
                        rs.getInt("SoBuoi"),
                        rs.getDate("NgayKhaiGiang"),
                        rs.getDouble("HocPhi"),
                        rs.getDate("NgayDangKy"),
                        rs.getString("TrangThai"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            isValidInput = true;
        }while (!isValidInput);
        System.out.println("+----------+----------+------------------+---------------------+-------------------------+--------------------------+----------+------------------+----------------+----------+----------+--------------+-----------+-------------------+------------+--------------+--------------+");
    }

    public static void suaThongTinDangKyDatabase()  {
        System.out.println("\n===========Sửa thông tin đăng ký==========");
        boolean isValidInput = false;
        do {
            try {
                System.out.println("Nhập MaDK cần sửa: ");
                int MaDK = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaDK)){
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                if (!kiemTraMaDKTonTai(MaDK)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }


                System.out.println("Nhập MaPH mới: ");
                int MaPH = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaPH)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }
                if (!kiemTraMaPHTonTai(MaPH)) {
                    System.out.println("Mã Phụ huynh không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("Nhập MaTre mới: ");
                int MaTre = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaTre)){
                    System.out.println("mã trẻ em không hợp lệ");
                    continue;
                }
                if (!kiemTraMaTreTonTai(MaTre)){
                    System.out.println("Mã trẻ em không tồn tại. Vui lòng nhập lại.");
                    continue;
                }


                System.out.println("nhập MaLH mới: ");
                int MaLH = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaLH)){
                    System.out.println("mã lớp học không hợp lệ");
                    continue;
                }
                if (!kiemTraMaLHTonTai(MaLH)){
                    System.out.println("Mã lớp học không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("nhập NgayDangKy mới (yyyy-MM-dd): ");
                String NgayDangKyStr = sc.nextLine();
                if (!InputValidator.validateDate(NgayDangKyStr)) {
                    System.out.println("Ngày đăng ký không hợp lệ.");
                    continue;
                }

                System.out.println("nhập TrangThai mới: ");
                String TrangThai = sc.nextLine();
                if (!InputValidator.validateTrangThai(TrangThai)) {
                    System.out.println("Trạng thái đăng ký không hợp lệ.");
                    continue;
                }

                String sql = "UPDATE DANGKYTRE SET MaPH=?, MaTre=?, MaLH=?, NgayDangKy=?, TrangThai=? WHERE MaDK=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, MaPH);
                pst.setInt(2, MaTre);
                pst.setInt(3, MaLH);
                pst.setDate(4, Date.valueOf(NgayDangKyStr));
                pst.setString(5, TrangThai);
                pst.setInt(6, MaDK);
                pst.executeUpdate();

                System.out.println("Thông tin đăng ký đã sửa thành công");
            } catch (NumberFormatException e) {
                System.out.println("lỗi: " );
            } catch (SQLException e) {
                System.out.println("vui lòng nhập lại: ");
            }
            isValidInput = true;
        }while (!isValidInput);
    }
    public static void xoaThongTinDangKyDatabase(){
        System.out.println("\n=========== Xóa thông tin đăng ký ==========");
        boolean isValidInput = false;
        do {
            try {
                System.out.println("Nhập MaDK cần xóa: ");
                int MaDK = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaDK)) {
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                if (!kiemTraMaDKTonTai(MaDK)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                String sql = "DELETE FROM DANGKYTRE WHERE MaDK=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, MaDK);
                int hangDuocXoa = pst.executeUpdate();

                if (hangDuocXoa > 0) {
                    System.out.println("Thông tin đăng ký đã được xóa thành công");
                } else {
                    System.out.println("Không tìm thấy MaDK tương ứng. Không có gì được xóa.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: ");
            } catch (SQLException e) {
                throw new RuntimeException("Lỗi SQL: ");
            }
            isValidInput = true;
        }while (!isValidInput);
    }

    public static void timKiemThongTinDangKyDatabase(){
        System.out.println("\n========================Tìm Kiếm thông tin đăng ký=========================");
        boolean isValidInput = false;
        do {
            try {
                System.out.println("Nhập MaDK cần tìm: ");
                int MaDK = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaDK)) {
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                if (!kiemTraMaDKTonTai(MaDK)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("+----------+----------+----------+----------+--------------+--------------+");
                System.out.println("|   MaDK   |   MaPH   |   MaTre  |   MaLH   |  NgayDangKy  |   TrangThai  |");
                System.out.println("+----------+----------+----------+----------+--------------+--------------+");
                String sql = "SELECT * FROM DANGKYTRE WHERE MaDK=?";

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, MaDK);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    System.out.printf("| %-8s | %-8s | %-8s | %-8s | %-12s | %-12s |\n",
                            rs.getInt("MaDK"),
                            rs.getInt("MaPH"),
                            rs.getInt("MaTre"),
                            rs.getInt("MaLH"),
                            rs.getDate("NgayDangKy"),
                            rs.getString("TrangThai"));
                } else {
                    System.out.println("không tìm thấy thông tin đăng ký");
                }
                rs.close();
            } catch (NumberFormatException | SQLException e) {
                System.out.println("Lỗi" + e);
            }
            isValidInput = true;
        }while (!isValidInput);
        System.out.println("+----------+----------+----------+----------+--------------+--------------+");
    }

    public static void lietKeThoiGianHoc(){
        System.out.println("\n==============Liệt kê thời gian học==============");
        System.out.println("+-----------+---------------+-------------------+");
        System.out.println("|  Ngày học |    Giờ học    | Số lượng đăng ký  |");
        System.out.println("+-----------+---------------+-------------------+");
        try {
            String sql = "SELECT TH.NgayHoc, TH.GioHoc, COUNT(DKT.MaDK) AS SoLuongDangKy\n" +
                    "FROM THOIGIANHOC TH\n" +
                    "INNER JOIN LOPHOC LH ON TH.MaTGH = LH.MaTGH\n" +
                    "INNER JOIN DANGKYTRE DKT ON LH.MaLH = DKT.MaLH\n" +
                    "WHERE DKT.NgayDangKy BETWEEN '2024-01-01' AND '2024-03-31'\n" +
                    "GROUP BY TH.NgayHoc, TH.GioHoc\n" +
                    "ORDER BY SoLuongDangKy DESC;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String ngayHoc = rs.getString("NgayHoc");
                String gioHoc = rs.getString("GioHoc");
                int soLuongDangKy = rs.getInt("SoLuongDangKy");

                System.out.printf("| %-9s | %-13s | %-17s |\n",ngayHoc, gioHoc, soLuongDangKy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("+-----------+---------------+-------------------+");
    }
}
