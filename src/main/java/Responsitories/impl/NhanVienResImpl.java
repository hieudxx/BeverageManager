/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import DomainModels.NhanVien;
import Responsitories.INhanVienRes;
import Utilities.JDBC_Helper;
import ViewModels.NhanVienViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Responsitories.INhanVienRes;

public class NhanVienResImpl implements INhanVienRes {

    @Override
    public NhanVien getOne(String TaiKhoan) {
        String query = """
                       SELECT id, ma_nhan_vien, ten_dang_nhap, mat_khau, ho_ten,
                       vai_tro, trang_thai_lam_viec FROM NhanVien WHERE ten_dang_nhap = ?""";
        ResultSet rs = JDBC_Helper.selectTongQuat(query, TaiKhoan);
        try {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getString("id"));
                nv.setMaNhanVien(rs.getString("ma_nhan_vien"));
                nv.setTenDangNhap(rs.getString("ten_dang_nhap"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setVaiTro(rs.getString("vai_tro"));
                nv.setTrangThai(rs.getBoolean("trang_thai_lam_viec"));
                return nv;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean add(NhanVien nv) {
        if (isEmployIdDuplicate(nv.getMaNhanVien())) {
            JOptionPane.showMessageDialog(null, "Đã tồn tại mã nhân viên");
            return false;
        } else if (isUsernameDuplicate(nv.getTenDangNhap())) {
            JOptionPane.showMessageDialog(null, "Đã tồn tại tài khoản");
            return false;
        } else {
            String sql = "insert into NhanVien(ma_nhan_vien, ten_dang_nhap, mat_khau, ho_ten, vai_tro, trang_thai_lam_viec) values (?,?,?,?,?,?)";
            JDBC_Helper.updateTongQuat(sql, nv.getMaNhanVien(), nv.getTenDangNhap(), nv.getMatKhau(), nv.getHoTen(), nv.getVaiTro(), nv.isTrangThai());
            return true;
        }
    }

    @Override
    public int update(String id, NhanVien nv) {
        String sql = "update NhanVien set ten_dang_nhap = ?, ho_ten = ?, vai_tro = ?, trang_thai_lam_viec =? where ma_nhan_vien = ? ";
        return JDBC_Helper.updateTongQuat(sql, nv.getTenDangNhap(), nv.getHoTen(), nv.getVaiTro(), nv.isTrangThai(), id);
    }

    @Override
    public int delete(String id) {
        String sql = "delete from NhanVien where ma_nhan_vien = ?";
        return JDBC_Helper.updateTongQuat(sql, id);
    }

    @Override
    public List<NhanVienViewModel> getAll() {
        List<NhanVienViewModel> listNV = new ArrayList<>();
        String sql = "select id, ma_nhan_vien, ten_dang_nhap, mat_khau, ho_ten, vai_tro, trang_thai_lam_viec FROM NhanVien";
        try {
            ResultSet rs = JDBC_Helper.selectTongQuat(sql);
            while (rs.next()) {
                NhanVienViewModel nv = new NhanVienViewModel();
                nv.setId(rs.getString("id"));
                nv.setMaNhanVien(rs.getString("ma_nhan_vien"));
                nv.setTenDangNhap(rs.getString("ten_dang_nhap"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setVaiTro(rs.getString("vai_tro"));
                nv.setTrangThai(rs.getBoolean("trang_thai_lam_viec"));
                listNV.add(nv);
            }
            return listNV;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean isEmployIdDuplicate(String maNhanVien) { // public boolean checktrungma(String ma) {
        NhanVien nv = getByTenDangNhap(maNhanVien);
        if (nv == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isUsernameDuplicate(String tenDangNhap) {
        NhanVien nv = getByTenDangNhap(tenDangNhap);
        if (nv == null) {
            return false;
        } else {
            return true;
        }
    }

    public NhanVien getByTenDangNhap(String tenDangNhap) {
        NhanVien nv = null;
        String sql = "select id, ma_nhan_vien, ten_dang_nhap, mat_khau, ho_ten, vai_tro, trang_thai_lam_viec from NhanVien where ten_dang_nhap = ?";
        try {

            ResultSet rs = JDBC_Helper.selectTongQuat(sql, tenDangNhap);
            while (rs.next()) {
                String id = rs.getString(1);
                String maNv = rs.getString(2);
                String tk = rs.getString(3);
                String matKhau = rs.getString(4);
                String hoTen = rs.getString(5);
                String vaiTro = rs.getString(6);
                boolean trangThai = rs.getBoolean(7);
                nv = new NhanVien(id, maNv, tk, matKhau, hoTen, vaiTro, trangThai);

            }
            return nv;
        } catch (SQLException e) {
            return null;
        }
    }
//    
//    public List<NhanVienViewModel> findByName(String name) {
//        List<NhanVienViewModel> listNv = new ArrayList<>();
//        String sql = "select id, ma_nhan_vien, ten_dang_nhap, ho_ten, vai_tro, trang_thai_lam_viec"
//                + " from NhanVien nv where ho_ten like N'" + name + "%' or ho_ten like N'%" + name + "' or ho_ten like N'%" + name + "%'";
//        ResultSet rs = JDBC_Helper.selectTongQuat(sql);
//        try {
//            while (rs.next()) {
//                String id = rs.getString(1);
//                String maNv = rs.getString(2);
//                String tenDangNhap = rs.getString(3);
//                String matKhau = rs.getString(4);
//                String hoTen = rs.getString(5);
//                String vaiTro = rs.getString(6);
//                boolean trangThai = rs.getBoolean(7);
//                NhanVienViewModel nv = new NhanVienViewModel(id, maNv, tenDangNhap, matKhau, hoTen, vaiTro, trangThai ? "Đang làm" : "Đã nghỉ");
//                listNv.add(nv);
//            }
//            return listNv;
//        } catch (SQLException e) {
//            return null;
//        }
//    }
//
//    public List<NhanVienViewModel> findByManv(String MaNv) {
//        List<NhanVienViewModel> listNv = new ArrayList<>();
//        String sql = "select id, ma_nhan_vien, ten_dang_nhap, ho_ten, vai_tro, trang_thai_lam_viec"
//                + " from NhanVien nv where ma_nhan_vien like N'" + MaNv + "%' or ma_nhan_vien like N'%" + MaNv + "' or ma_nhan_vien like N'%" + MaNv + "%'";
//        ResultSet rs = JDBC_Helper.selectTongQuat(sql);
//        try {
//            while (rs.next()) {
//                String id = rs.getString(1);
//                String maNv = rs.getString(2);
//                String tenDangNhap = rs.getString(3);
//                String matKhau = rs.getString(4);
//                String hoTen = rs.getString(5);
//                String vaiTro = rs.getString(6);
//                boolean trangThai = rs.getBoolean(7);
//                NhanVienViewModel nv = new NhanVienViewModel(id, maNv, tenDangNhap, matKhau, hoTen, vaiTro, trangThai ? "Đang làm" : "Đã nghỉ");
//                listNv.add(nv);
//            }
//            return listNv;
//        } catch (SQLException e) {
//            return null;
//        }
//    }
//
//    public NhanVien findByMaNv(String maNhanVien) {
//        NhanVien nv = null;
//        String sql = "select id, ma_nhan_vien, ten_dang_nhap, ho_ten, vai_tro, trang_thai_lam_viec from NhanVien where ma_nhan_vien = ?";
//        try {
//
//            ResultSet rs = JDBC_Helper.selectTongQuat(sql, maNhanVien);
//            while (rs.next()) {
//                String id = rs.getString(1);
//                String maNv = rs.getString(2);
//                String tenDangNhap = rs.getString(3);
//                String matKhau = rs.getString(4);
//                String hoTen = rs.getString(5);
//                String vaiTro = rs.getString(6);
//                boolean trangThai = rs.getBoolean(7);
//                nv = new NhanVien(id, maNv, tenDangNhap, matKhau, hoTen, vaiTro, trangThai);
//
//            }
//            return nv;
//        } catch (SQLException e) {
//            return null;
//        }
//    }
//
}
