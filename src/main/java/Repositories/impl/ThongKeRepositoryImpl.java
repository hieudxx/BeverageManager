/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import Repositories.ThongKeRepository;
import Utilities.JDBC_Helper;
import ViewModels.ThongKeSanPhamViewModel;
import ViewModels.ThongKeTongQuanViewModel;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ThongKeRepositoryImpl implements ThongKeRepository{
    
    private static final String TRANG_THAI_DA_THANH_TOAN = "Đã thanh toán";

    @Override
    public ThongKeTongQuanViewModel getTongQuan(LocalDateTime tuNgay, LocalDateTime denNgay) {
        ThongKeTongQuanViewModel result = new ThongKeTongQuanViewModel();

        String sql = "SELECT "
                + "COALESCE(SUM(hd.tong_tien), 0) AS TongDoanhThu, "
                + "COUNT(hd.id) AS TongSoHoaDon "
                + "FROM HoaDon hd "
                + "WHERE hd.trang_thai = ? "
                + "AND hd.ngay_tao >= ? "
                + "AND hd.ngay_tao < ?";

        ResultSet rs = JDBC_Helper.selectTongQuat(sql,
                TRANG_THAI_DA_THANH_TOAN,
                tuNgay,
                denNgay);

        try {
            if (rs != null && rs.next()) {
                BigDecimal doanhThu = rs.getBigDecimal("TongDoanhThu");
                result.setTongDoanhThu(doanhThu);
                result.setTongHoaDon(rs.getInt("TongSoHoaDon"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return result;
    }

    @Override
    public List<ThongKeSanPhamViewModel> getChiTietTheoSanPham(LocalDateTime tuNgay, LocalDateTime denNgay) {
        List<ThongKeSanPhamViewModel> list = new ArrayList<>();

        /*
         * Một sản phẩm có thể có nhiều SizeSanPham và mỗi size có giá bán khác nhau.
         * Vì vậy "Đơn giá" trên báo cáo được tính là đơn giá bán bình quân có trọng số:
         *     SUM(so_luong * gia_luc_ban) / SUM(so_luong)
         * Còn "Thành tiền" là tổng tiền thực tế của sản phẩm.
         */
        String sql = "SELECT "
                + "ROW_NUMBER() OVER (ORDER BY SUM(hdct.so_luong * hdct.gia_luc_ban) DESC, sp.ten_san_pham ASC) AS STT, "
                + "sp.ma_san_pham AS MaSanPham, "
                + "sp.ten_san_pham AS TenSanPham, "
                + "SUM(hdct.so_luong) AS TongSoLuongBan, "
                + "CAST(SUM(hdct.so_luong * hdct.gia_luc_ban) / NULLIF(SUM(hdct.so_luong), 0) AS DECIMAL(18, 0)) AS DonGia, "
                + "SUM(hdct.so_luong * hdct.gia_luc_ban) AS ThanhTien "
                + "FROM HoaDonChiTiet hdct "
                + "JOIN HoaDon hd ON hdct.id_hoa_don = hd.id "
                + "JOIN SizeSanPham ssp ON hdct.id_size = ssp.id "
                + "JOIN SanPham sp ON ssp.id_san_pham = sp.id "
                + "WHERE hd.trang_thai = ? "
                + "AND hd.ngay_tao >= ? "
                + "AND hd.ngay_tao < ? "
                + "GROUP BY sp.id, sp.ma_san_pham, sp.ten_san_pham "
                + "ORDER BY SUM(hdct.so_luong * hdct.gia_luc_ban) DESC, sp.ten_san_pham ASC";

        ResultSet rs = JDBC_Helper.selectTongQuat(sql,
                TRANG_THAI_DA_THANH_TOAN,
                tuNgay,
                denNgay);

        try {
            while (rs != null && rs.next()) {
                ThongKeSanPhamViewModel item = new ThongKeSanPhamViewModel();
                item.setStt(rs.getInt("STT"));
                item.setMaSanPham(rs.getString("MaSanPham"));
                item.setTenSanPham(rs.getString("TenSanPham"));
                item.setSoLuongBan(rs.getInt("TongSoLuongBan"));
                item.setDonGia(rs.getBigDecimal("DonGia"));
                item.setThanhTien(rs.getBigDecimal("ThanhTien"));
                list.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return list;
    }

    @Override
    public ThongKeTongQuanViewModel getMonBanChayNhat(LocalDateTime tuNgay, LocalDateTime denNgay) {
        ThongKeTongQuanViewModel result = new ThongKeTongQuanViewModel();

        String sql = "SELECT TOP 1 "
                + "sp.ten_san_pham AS TenSanPham, "
                + "SUM(hdct.so_luong) AS TongSoLuong "
                + "FROM HoaDonChiTiet hdct "
                + "JOIN HoaDon hd ON hdct.id_hoa_don = hd.id "
                + "JOIN SizeSanPham ssp ON hdct.id_size = ssp.id "
                + "JOIN SanPham sp ON ssp.id_san_pham = sp.id "
                + "WHERE hd.trang_thai = ? "
                + "AND hd.ngay_tao >= ? "
                + "AND hd.ngay_tao < ? "
                + "GROUP BY sp.id, sp.ten_san_pham "
                + "ORDER BY SUM(hdct.so_luong) DESC, sp.ten_san_pham ASC";

        ResultSet rs = JDBC_Helper.selectTongQuat(sql,
                TRANG_THAI_DA_THANH_TOAN,
                tuNgay,
                denNgay);

        try {
            if (rs != null && rs.next()) {
                result.setMonBanChayNhat(rs.getString("TenSanPham"));
                result.setSoLuongMonBanChayNhat(rs.getInt("TongSoLuong"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return result;
    }

    private void closeResultSet(ResultSet rs) {
        if (rs == null) {
            return;
        }
        try {
            if (!rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
