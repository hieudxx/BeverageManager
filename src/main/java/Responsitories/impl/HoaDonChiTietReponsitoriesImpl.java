/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import DomainModels.HoaDon;
import DomainModels.HoaDonChiTiet;
import DomainModels.SanPham;
import Responsitories.HoaDonChiTietResponsitories;
import Utilities.JDBC_Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class HoaDonChiTietReponsitoriesImpl implements HoaDonChiTietResponsitories{

    @Override
    public List<HoaDonChiTiet> selectByID(String idHoaDon) {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        String query = "SELECT id, IDSP, MaSP, TenSP, DonGia, SoLuong, ThanhToan FROM HoaDonChiTiet LEFT JOIN SanPham ON HoaDonChiTiet.IDSP = SanPham.ID WHERE IDHD = ?";
        ResultSet rs = JDBC_Helper.selectTongQuat(query, idHoaDon);
        try {
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                HoaDon hd = new HoaDon();
//                SanPham sp = new SanPham();
//                hd.setID(rs.getString("IDHD"));
//                sp.setID(rs.getString("IDSP"));
//                sp.setTenSP(rs.getString("TenSP"));
//                sp.setMaSP(rs.getString("MaSP"));
//                hdct.setHd(hd);
//                hdct.setSp(sp);
//                hdct.setDonGia(rs.getDouble("DonGia"));
//                hdct.setSoLuong(rs.getInt("SoLuong"));
//                hdct.setThanhToan(rs.getDouble("ThanhToan"));
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
        String query = "INSERT INTO [dbo].[HoaDonChiTiet] ([IDHD], [IDSP], [DonGia], [SoLuong], [ThanhToan]) VALUES (?, ?, ?, ?, ?)";
        return 
//                JDBC_Helper.updateTongQuat(query, hdct.getHd().getID(), hdct.getSp().getID(), hdct.getDonGia(), hdct.getSoLuong(), hdct.getThanhToan())
                0;
    }

    @Override
    public int delete(String IDHD) {
        String query = "DELETE FROM [dbo].[HoaDonChiTiet] WHERE IDHD = ?";
        return JDBC_Helper.updateTongQuat(query, IDHD);
    }

    @Override
    public List<HoaDonChiTiet> getSpByID(String idsanpham) {
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
    
}
