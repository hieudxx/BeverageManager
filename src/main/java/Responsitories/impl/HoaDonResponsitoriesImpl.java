/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import DomainModels.HoaDon;
import DomainModels.KhachHang;
import DomainModels.NhanVien;
import Responsitories.HoaDonResponsitories;
import Utilities.JDBC_Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class HoaDonResponsitoriesImpl implements HoaDonResponsitories{
    
        @Override
    public HoaDon selectByMaHD(String maHD) {
        String query = "SELECT HoaDon.id, ma_hoa_don, NhanVien.ma_nhan_vien, NhanVien.ho_ten as 'HoTenNV', KhachHang.ma_khach_hang, KhachHang.ho_ten as 'HoTenKH', KhachHang.so_dien_thoai, \n" +
"                       HoaDon.ngay_tao,trang_thai,phuong_thuc_tt,tong_tien, tien_thanh_toan\n" +
"                       FROM HoaDon LEFT JOIN NhanVien ON HoaDon.id_nhan_vien = NhanVien.id\n" +
"                                   LEFT JOIN KhachHang ON HoaDon.id_khach_hang = KhachHang.id Where HoaDon.ma_hoa_don = ?";
        ResultSet rs = JDBC_Helper.selectTongQuat(query, maHD);
        try {                
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                NhanVien nv = new NhanVien();
                KhachHang kh = new KhachHang();
                nv.setTenDangNhap(rs.getString("ten_dang_nhap")); nv.setHoTen(rs.getString("ho_ten"));
                hd.setId(rs.getString("id"));
                hd.setMaHoaDon(rs.getString("ma_hoa_don"));
                hd.setNhanVien(nv); hd.setNgayTao(rs.getObject("ngay_tao", LocalDateTime.class)); hd.setTrangThai(rs.getString("trang_thai"));
                kh.setMaKhachHang(rs.getString("ma_khach_hang")); kh.setHoTen(rs.getString("ho_ten")); kh.setSoDienThoai(rs.getString("so_dien_thoai"));
                hd.setKhachHang(kh);
                hd.setTongTien(rs.getBigDecimal("tong_tien"));
                hd.setTienThanhToan(rs.getBigDecimal("tien_thanh_toan"));
                hd.setPhuongThucTT(rs.getString("phuong_thuc_tt"));
                return hd;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<HoaDon> selectByHDChoTT() {
        List<HoaDon> listHD = new ArrayList<>();
        String query = "SELECT HoaDon.id, ma_hoa_don, NhanVien.ma_nhan_vien, NhanVien.ho_ten as 'HoTenNV', KhachHang.ma_khach_hang, KhachHang.ho_ten as 'HoTenKH', KhachHang.so_dien_thoai, \n" +
"                       HoaDon.ngay_tao,trang_thai,phuong_thuc_tt,tong_tien, tien_thanh_toan\n" +
"                       FROM HoaDon LEFT JOIN NhanVien ON HoaDon.id_nhan_vien = NhanVien.id\n" +
"                                   LEFT JOIN KhachHang ON HoaDon.id_khach_hang = KhachHang.id Where HoaDon.trang_thai LIKE N'%Chờ thanh toán%'";
        ResultSet rs = JDBC_Helper.selectTongQuat(query);
        try {                
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                NhanVien nv = new NhanVien();
                KhachHang kh = new KhachHang();
//                nv.setTenDangNhap(rs.getString("ten_dang_nhap")); 
                nv.setHoTen(rs.getString("HoTenNV"));
//vì đã đặt tên bảng tạm là as 'HoTenNV' nên giá trị  phải để tên giống bảng tên trong bảng tạm
                nv.setMaNhanVien(rs.getString("ma_nhan_vien"));
                hd.setId(rs.getString("id"));
                hd.setMaHoaDon(rs.getString("ma_hoa_don"));
                hd.setNhanVien(nv); hd.setNgayTao(rs.getObject("ngay_tao", LocalDateTime.class)); hd.setTrangThai(rs.getString("trang_thai"));
                kh.setMaKhachHang(rs.getString("ma_khach_hang")); kh.setHoTen(rs.getString("HoTenKH")); kh.setSoDienThoai(rs.getString("so_dien_thoai"));
                hd.setKhachHang(kh);
                hd.setTongTien(rs.getBigDecimal("tong_tien"));
                hd.setTienThanhToan(rs.getBigDecimal("tien_thanh_toan"));
                hd.setPhuongThucTT(rs.getString("phuong_thuc_tt"));
                listHD.add(hd);
            }
            return listHD;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public int update(HoaDon hd) {
        String query = "UPDATE HoaDon SET [id_nhan_vien] = ?, [id_khach_hang] = ?, \n" +
                       " [tong_tien] = ?,[tien_thanh_toan] = ?, [phuong_thuc_tt] = ?, [trang_thai] = ? WHERE ma_hoa_don = ?";
        return JDBC_Helper.updateTongQuat(query, hd.getNhanVien().getId(), hd.getTongTien(),
        hd.getTienThanhToan(), hd.getPhuongThucTT(), hd.getTrangThai(), hd.getMaHoaDon()
        );
    }
    
    @Override
    public int insert(HoaDon hd) {
        String query = "INSERT INTO [dbo].[HoaDon] ([ma_hoa_don],[id_nhan_vien],[id_khach_hang], [ngay_tao], \n" +
                       "[tong_tien], [tien_thanh_toan], [phuong_thuc_tt], [trang_thai]) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        return JDBC_Helper.updateTongQuat(query,
        hd.getMaHoaDon(),
        hd.getNhanVien() != null ? hd.getNhanVien().getId() : null,   // 1
        hd.getKhachHang() != null ? hd.getKhachHang().getId() : null, // 2
        hd.getNgayTao(),                                              // 3
        hd.getTongTien() != null ? hd.getTongTien() : 0,              // 4 (Tránh lỗi nếu BigDecimal bị null)
        hd.getTienThanhToan() != null ? hd.getTienThanhToan() : 0,    // 5
        hd.getPhuongThucTT(),                                         // 6
        hd.getTrangThai()
        );
    }

    @Override
    public int updateNoKH(HoaDon hd) {
         String query = "UPDATE [dbo].[HoaDon] SET [id_nhan_vien] = ?, \n" +
                       " [tong_tien] = ?,[tien_thanh_toan] = ?, [phuong_thuc_tt] = ?, [trang_thai] = ? WHERE ma_hoa_don = ?";
        return JDBC_Helper.updateTongQuat(query, hd.getNhanVien().getId(), hd.getTongTien(),
        hd.getTienThanhToan(), hd.getPhuongThucTT(), hd.getTrangThai(), hd.getMaHoaDon()
        );
    }
}
