package Responsitories.impl;

import DomainModels.KhachHang;
import Responsitories.IKhachHangRes;
import Utilities.JDBC_Helper;
import ViewModels.KhachHangViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhachHangResImpl implements IKhachHangRes {

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
        String sql = "INSERT INTO KhachHang (ma_khach_hang, so_dien_thoai, ho_ten, trang_thai) VALUES (?, ?, ?, ?)";
        try {
            int result = JDBC_Helper.updateTongQuat(sql, kh.getMaKhachHang(), kh.getSoDienThoai(), kh.getHoTen(), 1);
            return result > 0;
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg != null) {
                if (msg.contains("ma_kh_active")) {
                    throw new RuntimeException("Mã khách hàng đã tồn tại!");
                }
                if (msg.contains("sdt_active")) {
                    throw new RuntimeException("Số điện thoại đã tồn tại!");
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Thêm khách hàng thất bại!");
        }
    }

    @Override
    public boolean update(String maKh, KhachHang kh) {
        String sql = "UPDATE KhachHang SET so_dien_thoai = ?, ho_ten = ? "
                + "WHERE ma_khach_hang = ? AND trang_thai = 1";

        try {
            int result = JDBC_Helper.updateTongQuat(sql, kh.getSoDienThoai(), kh.getHoTen(), maKh);
            return result > 0;

        } catch (Exception e) {
            String msg = e.getMessage();

            if (msg != null) {
                if (msg.contains("ma_kh_active")) {
                    throw new RuntimeException("Mã khách hàng đã tồn tại!");
                }
                if (msg.contains("sdt_active")) {
                    throw new RuntimeException("Số điện thoại đã tồn tại!");
                }
            }
            throw new RuntimeException("Cập nhật khách hàng thất bại!");
        }
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
}
