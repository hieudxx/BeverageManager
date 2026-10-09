package Repositories.impl;

import DomainModels.KhachHang;
import Utilities.JDBC_Helper;
import ViewModels.KhachHangViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Repositories.KhachHangRepository;

public class KhachHangResImpl implements KhachHangRepository {

    @Override
    public List<KhachHangViewModel> getAll() {
        List<KhachHangViewModel> listKh = new ArrayList<>();
        String sql = "select id, ma_khach_hang, ho_ten, so_dien_thoai, trang_thai FROM KhachHang WHERE trang_thai = 1";
        try {
            ResultSet rs = JDBC_Helper.selectTongQuat(sql);
            while (rs.next()) {
                KhachHangViewModel kh = new KhachHangViewModel();
                kh.setId(rs.getInt("id"));
                kh.setMaKhachHang(rs.getString("ma_khach_hang"));
                kh.setHoTen(rs.getString("ho_ten"));
                kh.setSoDienThoai(rs.getString("so_dien_thoai"));
                kh.setTrangThai(rs.getBoolean("trang_thai"));
                listKh.add(kh);
            }
            return listKh;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(KhachHang kh) {
        // Luôn tạo bản ghi MỚI. Khách đã xóa mềm (trang_thai = 0) được giữ nguyên để lưu lịch sử hóa đơn.
        // Chỉ mục sdt_active chỉ tính khách đang hoạt động, nên:
        //  - SĐT thuộc khách đã xóa mềm  -> cho phép tạo mới
        //  - SĐT thuộc khách đang hoạt động -> bị chặn, báo "Số điện thoại đã tồn tại!"
        String sql = "INSERT INTO KhachHang (ma_khach_hang, so_dien_thoai, ho_ten, trang_thai) VALUES (?, ?, ?, ?)";
        try {
            return JDBC_Helper.updateOrThrow(sql, kh.getMaKhachHang(), kh.getSoDienThoai(), kh.getHoTen(), 1) > 0;
        } catch (SQLException e) {
            throw chuyenLoi(e, "Thêm khách hàng thất bại!");
        }
    }

    @Override
    public boolean update(String maKh, KhachHang kh) {
        String sql = "UPDATE KhachHang SET so_dien_thoai = ?, ho_ten = ? "
                + "WHERE ma_khach_hang = ? AND trang_thai = 1";
        try {
            return JDBC_Helper.updateOrThrow(sql, kh.getSoDienThoai(), kh.getHoTen(), maKh) > 0;
        } catch (SQLException e) {
            throw chuyenLoi(e, "Cập nhật khách hàng thất bại!");
        }
    }

    // Đổi lỗi SQL thành thông báo dễ hiểu (tên unique index nằm trong nội dung lỗi của SQL Server)
    private RuntimeException chuyenLoi(SQLException e, String thongBaoMacDinh) {
        String msg = e.getMessage();
        if (msg != null) {
            if (msg.contains("sdt_active")) {
                return new RuntimeException("Số điện thoại đã tồn tại!");
            }
            if (msg.contains("ma_kh_active")) {
                return new RuntimeException("Mã khách hàng đã tồn tại!");
            }
        }
        e.printStackTrace();
        return new RuntimeException(thongBaoMacDinh);
    }

    @Override
    public boolean delete(String maKH) {
        String sql = "UPDATE KhachHang SET trang_thai = 0 WHERE ma_khach_hang = ?";

        try {
            int result = JDBC_Helper.updateTongQuat(sql, maKH);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Xóa khách hàng thất bại!");
        }
    }

    @Override
    public KhachHangViewModel getBySdt(String sdt) {
        String sql = "SELECT id, ma_khach_hang, ho_ten, so_dien_thoai, trang_thai "
               + "FROM KhachHang WHERE so_dien_thoai = ? AND trang_thai = 1";
    try {
        ResultSet rs = JDBC_Helper.selectTongQuat(sql, sdt);
        if (rs.next()) {
            KhachHangViewModel kh = new KhachHangViewModel();
            kh.setId(rs.getInt("id"));
            kh.setMaKhachHang(rs.getString("ma_khach_hang"));
            kh.setHoTen(rs.getString("ho_ten"));
            kh.setSoDienThoai(rs.getString("so_dien_thoai"));
            kh.setTrangThai(rs.getBoolean("trang_thai"));
            return kh;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
    }
}
