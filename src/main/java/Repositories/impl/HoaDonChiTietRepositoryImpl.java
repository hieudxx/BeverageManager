/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import DomainModels.HoaDon;
import DomainModels.HoaDonChiTiet;
import DomainModels.SanPham;
import DomainModels.Size;
import Utilities.JDBC_Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Repositories.HoaDonChiTietRepository;

/**
 *
 * @author admin
 */
public class HoaDonChiTietRepositoryImpl implements HoaDonChiTietRepository{

    @Override
    public List<HoaDonChiTiet> selectByID(int idHoaDon) {
List<HoaDonChiTiet> listHDCT = new ArrayList<>();
    
    // Câu truy vấn JOIN 3 bảng: HoaDonChiTiet -> SizeSanPham -> SanPham
    String query = "SELECT hdct.id, hdct.id_hoa_don, hdct.id_size, " +
                   "sp.ten_san_pham, ssp.ten_size, hdct.so_luong, hdct.gia_luc_ban " +
                   "FROM HoaDonChiTiet hdct " +
                   "JOIN SizeSanPham ssp ON hdct.id_size = ssp.id " +
                   "JOIN SanPham sp ON ssp.id_san_pham = sp.id " +
                   "WHERE hdct.id_hoa_don = ?";
    
    ResultSet rs = JDBC_Helper.selectTongQuat(query, idHoaDon);
    try {
        while (rs.next()) {
            // 1. Tạo đối tượng HoaDonChiTiet
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setId(rs.getInt("id"));
            hdct.setSoLuong(rs.getInt("so_luong"));
            hdct.setGiaLucBan(rs.getBigDecimal("gia_luc_ban"));
            
            // 2. Tạo đối tượng HoaDon (để set vào hdct)
            HoaDon hd = new HoaDon();
            hd.setId(rs.getInt("id_hoa_don"));
            hdct.setHoaDon(hd);
            
            // 3. Tạo đối tượng SizeSanPham và SanPham để lấy tên hiển thị
            Size ssp = new Size();
            ssp.setId(rs.getInt("id_size"));
            ssp.setTenSize(rs.getString("ten_size"));
            
            SanPham sp = new SanPham();
            sp.setTenSanPham(rs.getString("ten_san_pham"));
            
            // Liên kết: Size thuộc về Sản phẩm
            ssp.setSanPham(sp); 
            // Liên kết: HDCT thuộc về Size này
            hdct.setSize(ssp); 

            listHDCT.add(hdct);
        }
        return listHDCT;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return null;
    }
    }

    @Override
    public int insert(HoaDonChiTiet hdct) {
        // 1. Kiểm tra sản phẩm cùng size đã tồn tại trong hóa đơn đó chưa
    String checkQuery = "SELECT id FROM HoaDonChiTiet WHERE id_hoa_don = ? AND id_size = ?";
    ResultSet rs = JDBC_Helper.selectTongQuat(checkQuery, 
            hdct.getHoaDon().getId(), 
            hdct.getSize().getId());
    
    try {
        if (rs.next()) {
            // 2. Nếu đã có -> Update cộng thêm số lượng
            String updateQuery = "UPDATE HoaDonChiTiet SET so_luong = so_luong + ? WHERE id = ?";
            return JDBC_Helper.updateTongQuat(updateQuery, hdct.getSoLuong(), rs.getInt("id"));
        } else {
            // 3. Nếu chưa có -> Insert dòng mới
            String insertQuery = "INSERT INTO HoaDonChiTiet (id_hoa_don, id_size, so_luong, gia_luc_ban) VALUES (?, ?, ?, ?)";
            return JDBC_Helper.updateTongQuat(insertQuery, 
                    hdct.getHoaDon().getId(), 
                    hdct.getSize().getId(), 
                    hdct.getSoLuong(), 
                    hdct.getGiaLucBan());
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return 0;
    }
    }

    @Override
    public int delete(int IDHD) {
        String query = "DELETE FROM [dbo].[HoaDonChiTiet] WHERE id_hoa_don = ?";
        return JDBC_Helper.updateTongQuat(query, IDHD);
    }

    @Override
    public List<HoaDonChiTiet> getSpByID(int idsanpham) {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        String query = "SELECT IDHD, sp.ID, sp.MaSP, sp.TenSP, DonGia, SoLuong, ThanhToan FROM HoaDonChiTiet LEFT JOIN SanPham sp ON HoaDonChiTiet.IDSP = SP.ID WHERE sp.MaSP = ?";
        ResultSet rs = JDBC_Helper.selectTongQuat(query, idsanpham);
        try {
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
//                HoaDon hd = new HoaDon();
//                SanPham sp = new SanPham();
//                hd.setID(rs.getString(1));
//                sp.setID(rs.getString(2));
//                sp.setTenSP(rs.getString(4));
//                sp.setMaSP(rs.getString(3));
//                hdct.setHd(hd);
//                hdct.setSp(sp);
//                hdct.setDonGia(rs.getDouble(5));
//                hdct.setSoLuong(rs.getInt(6));
//                hdct.setThanhToan(rs.getDouble(7));
                listHDCT.add(hdct);
            }
            return listHDCT;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<HoaDonChiTiet> getAll() {
        List<HoaDonChiTiet> hdct = new ArrayList<>();
        String sql = "select hdct.ID,hd.MaHD, sp.MaSP, sp.TenSP, hdct.DonGia, hdct.SoLuong, hdct.ThanhToan from HoaDonChiTiet hdct join SanPham sp on hdct.IDSP = sp.ID join HoaDon hd on hdct.IDHD = hd.ID ";
        ResultSet rs = JDBC_Helper.selectTongQuat(sql);
        try {
            while (rs.next()) {
//                String id = rs.getString(1);
//                String mahd = rs.getString(2);
//                String masp = rs.getString(3);
//                String tensp = rs.getString(4);
//                double dongia = rs.getDouble(5);
//                int soluong = rs.getInt(6);
//                double thanhtoan = rs.getDouble(7);
//                HoaDonChiTiet hdct2 = new HoaDonChiTiet(id, mahd, masp, tensp, dongia, soluong, thanhtoan);
//                hdct.add(hdct2);
            }
            return hdct;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteOne(int idHDCT) {
        String query = "DELETE FROM HoaDonChiTiet WHERE id = ?";
    return JDBC_Helper.updateTongQuat(query, idHDCT);
    }
    
}
