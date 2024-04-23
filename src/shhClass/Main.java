package shhClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<DangKyTre> dangKyTres = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static Connection conn = DatabaseManager.getConnection();

    public static void main(String[] args) {
        DatabaseManager.getConnection();
        boolean exit = false;
        while (!exit){
            try {
                System.out.println("\n==================MENU==================");
                System.out.println("|       1.thêm thông tin đăng ký       |");
                System.out.println("|       2.Sửa thông tin đăng ký        |");
                System.out.println("|       3.Xóa thông tin đăng ký        |");
                System.out.println("|       4.Tìm kiếm thông tin đăng ký   |");
                System.out.println("|       5.Liệt kê thời gian học        |");
                System.out.println("========================================");
                System.out.println("Chọn chức năng: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:
                        themThongTinDangKy();
                        break;
                    case 2:
                        suaThongTinDangKyDatabase();
                        break;
                    case 3:
                        xoaThongTinDangKyDatabase();
                        break;
                    case 4:
                        timKiemThongTinDangKyDatabase();
                        break;
                    case 5:
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
        DatabaseManager.closeConnection();
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

                System.out.println("Nhập mã trẻ em: ");
                int MaTre = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaTre)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }

                System.out.println("Nhập mã lớp: ");
                int MaLH = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaLH)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }

                System.out.println("Nhập ngày đăng ký (yyyy-MM-dd):");
                String ngayDangKyStr = sc.nextLine();
                if (!InputValidator.validateDate(ngayDangKyStr)) {
                    System.out.println("Ngày đăng ký không hợp lệ.");
                    continue;
                }

                System.out.println("nhập trạng thái đăng ký: ");
                String trangThai = sc.nextLine();
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
            }
        } while (!isValidInput);
    }
    public static void themDangKyHocSinhVaoDatabase(DangKyTre dangKyTre){
        try {
            String sql = "INSERT INTO DANGKYTRE(MaPH,MaTre,MaLH,NgayDangKy,TrangThai) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, dangKyTre.getMaLH());
            preparedStatement.setInt(2,dangKyTre.getMaTre());
            preparedStatement.setInt(3,dangKyTre.getMaLH());
            preparedStatement.setDate(4,dangKyTre.getNgayDangKy());
            preparedStatement.setString(5,dangKyTre.getTrangThai());
            preparedStatement.executeUpdate();
            System.out.println("đã thêm thông tin đăng ký thành công");

        } catch (SQLException e) {
            System.out.println("vui lòng nhập lại");
        }
    }
    public static boolean kiemTraMaDKTonTai(int MaDK) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM DANGKYTRE WHERE MaDK = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, MaDK);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            return count > 0;
        }
        return false;
    }
    public static void suaThongTinDangKyDatabase()  {
        System.out.println("\n===========Sửa thông tin đăng ký==========");
        boolean isValidInput = false;
        do {
            try {
                System.out.println("Nhập MaDK cần sửa: ");
                int MaDK = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaDK)){
                    System.out.println("mã phụ huynh không hợp lệ");
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

                System.out.println("Nhập MaTre mới: ");
                int MaTre = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaTre)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }

                System.out.println("nhập MaLH mới: ");
                int MaLH = Integer.parseInt(sc.nextLine());
                if (!InputValidator.validateMa(MaLH)){
                    System.out.println("mã phụ huynh không hợp lệ");
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
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, MaPH);
                preparedStatement.setInt(2, MaTre);
                preparedStatement.setInt(3, MaLH);
                preparedStatement.setDate(4, Date.valueOf(NgayDangKyStr));
                preparedStatement.setString(5, TrangThai);
                preparedStatement.setInt(6, MaDK);
                preparedStatement.executeUpdate();

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
        try {
            System.out.println("Nhập MaDK cần xóa: ");
            int MaDK = Integer.parseInt(sc.nextLine());

            String sql = "DELETE FROM DANGKYTRE WHERE MaDK=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, MaDK);

            int hangDuocXoa = preparedStatement.executeUpdate();

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
    }

    public static void timKiemThongTinDangKyDatabase(){
        System.out.println("\n========================Tìm Kiếm thông tin đăng ký=========================");
        try {
            System.out.println("Nhập MaDK cần tìm: ");
            int MaDK = Integer.parseInt(sc.nextLine());

            System.out.println("+----------+----------+----------+----------+--------------+--------------+");
            System.out.println("|   MaDK   |   MaPH   |   MaTre  |   MaLH   |  NgayDangKy  |   TrangThai  |");
            System.out.println("+----------+----------+----------+----------+--------------+--------------+");
            String sql = "SELECT * FROM DANGKYTRE WHERE MaDK=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, MaDK);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
                System.out.printf("| %-8s | %-8s | %-8s | %-8s | %-12s | %-12s |\n",
                                    rs.getInt("MaDK"),
                                    rs.getInt("MaPH"),
                                    rs.getInt("MaTre"),
                                    rs.getInt("MaLH"),
                                    rs.getDate("NgayDangKy"),
                                    rs.getString("TrangThai"));
            }
            else {
                System.out.println("không tìm thấy thông tin đăng ký");
            }
            rs.close();
        }
        catch (NumberFormatException | SQLException e){
            System.out.println("Lỗi" + e);
        }
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
