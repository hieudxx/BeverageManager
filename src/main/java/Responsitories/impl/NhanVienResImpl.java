/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import DomainModels.NhanVien;
import Responsitories.INhanVienRes;
import Utilities.JDBC_Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NhanVienResImpl implements INhanVienRes {

    @Override
    public List<NhanVien> getALL() {
        List<NhanVien> listNhanVien = new ArrayList<>();
        String query = "SELECT [ID], [ten_dang_nhap], [mat_khau], [ho_ten],\n"
                + "[vai_tro], [trang_thai_lam_viec]FROM [dbo].[NhanVien]";
        ResultSet rs = JDBC_Helper.selectTongQuat(query);
        try {
            while (rs.next()) {
//                ChucVu cv = new ChucVu();
                NhanVien nv = new NhanVien();
                nv.setID(rs.getString("ID"));
                nv.setTenDangNhap(rs.getString("ten_dang_nhap"));
                nv.setMatKhau(rs.getString("ma"));
                nv.setHoTen(rs.getString("hoTen"));

//                cv.setID(rs.getString("IDCV"));
//                nv.setCv(cv);
                nv.setTrangThai(rs.getBoolean("trangThai"));
                listNhanVien.add(nv);
            }
            return listNhanVien;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public NhanVien getOne(String TaiKhoan) {
        String query = "SELECT [ID], [ten_dang_nhap], [mat_khau], [ho_ten],\n"
                + "[vai_tro], [trang_thai_lam_viec]FROM [dbo].[NhanVien] WHERE TaiKhoan = ?";
        ResultSet rs = JDBC_Helper.selectTongQuat(query, TaiKhoan);
        try {
            while (rs.next()) {
//                ChucVu cv = new ChucVu();
                NhanVien nv = new NhanVien();
                nv.setID(rs.getString("ID"));
                nv.setTenDangNhap(rs.getString("ten_dang_nhap"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setHoTen(rs.getString("ho_ten"));
//                cv.setID(rs.getString("IDCV"));
//                nv.setCv(cv);
                nv.setTrangThai(rs.getBoolean("trang_thai_lam_viec"));
                return nv;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(NhanVien nv) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(NhanVien nv) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String maNhanVien) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
