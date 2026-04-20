///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Responsitories;
//
//import DomainModels.NhanVien;
//import Utilities.JDBC_Helper;
//import ViewModels.NhanVienViewModel;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//import javax.swing.JOptionPane;
//
///**
// *
// * @author Admin
// */
//public class NhanVienRes {
//
//    public List<NhanVienViewModel> getAll() {
//        List<NhanVienViewModel> listNV = new ArrayList<>();
//        String sql = "select nv.Id, nv.ma_nhan_vien, nv.ten_dang_nhap, nv.mat_khau, nv.ho_ten, nv.vai_tro, nv.trang_thai_lam_viec  from NhanVien";
//        try {
//            ResultSet rs = JDBC_Helper.selectTongQuat(sql);
//            while (rs.next()) {
//                String id = rs.getString(1);
//                String maNhanVien = rs.getString(2);
//                String tenDangNhap = rs.getString(3);
//                String matKhau = rs.getString(4);
//                String hoTen = rs.getString(5);
//                String vaiTro = rs.getString(6);
//                boolean trangThai = rs.getBoolean(7);
//                NhanVienViewModel nv = new NhanVienViewModel(id, maNhanVien, tenDangNhap, matKhau, hoTen, vaiTro, trangThai);
//                listNV.add(nv);
//            }
//            return listNV;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public NhanVienViewModel getByMaNv(String maNhanVien) {
//        NhanVienViewModel nv = null;
//        String sql = "select nv.id, nv.ma_nhan_vien, nv.ten_dang_nhap, nv.mat_khau, nv.ho_ten, nv.vai_tro, nv.trang_thai_lam_viec from NhanVien nv where nv.ma_nhan_vien = ?";
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
//                nv = new NhanVienViewModel(id, maNv, tenDangNhap, matKhau, hoTen, vaiTro, trangThai);
//
//            }
//            return nv;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public NhanVien findByMaNv(String maNhanVien) {
//        NhanVien nv = null;
//        String sql = "select id, ma_nhan_vien, ten_dang_nhap, mat_khau, ho_ten, vai_tro, trang_thai_lam_viec from NhanVien where ma_nhan_vien = ?";
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
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public NhanVien getByTenDangNhap(String tenDangNhap) {
//        NhanVien nv = null;
//        String sql = "select id, ma_nhan_vien, ten_dang_nhap, mat_khau, ho_ten, vai_tro, trang_thai_lam_viec from NhanVien where ten_dang_nhap = ?";
//        try {
//
//            ResultSet rs = JDBC_Helper.selectTongQuat(sql, tk);
//            while (rs.next()) {
//                String id = rs.getString(1);
//                String maNv = rs.getString(2);
//                String tk = rs.getString(3);
//                String matKhau = rs.getString(4);
//                String hoTen = rs.getString(5);
//                String vaiTro = rs.getString(6);
//                boolean trangThai = rs.getBoolean(7);
//                nv = new NhanVien(id, maNv, tk, matKhau, hoTen, vaiTro, trangThai);
//
//            }
//            return nv;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public boolean checktrungma(String ma) {
//        NhanVienModel nv2 = getnvbyma2(ma);
//        if (nv2 == null) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean checktrungtk(String tk) {
//        NhanVienModel nv3 = getnvbytk(tk);
//        if (nv3 == null) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean add(NhanVienModel nv) {
//
//        if (checktrungma(nv.getManv())) {
//            JOptionPane.showMessageDialog(null, "Đã tồn tại mã nhân viên");
//            return false;
//        } else if (checktrungtk(nv.getTentk())) {
//            JOptionPane.showMessageDialog(null, "Đã tồn tại tài khoản");
//            return false;
//        } else {
//            String sql = "insert into NhanVien(Manv, taiKhoan, matKhau, hoTen, gioiTinh, ngaySinh, SDT, diaChi, IDCV, trangThai) values (?,?,?,?,?,?,?,?,?,?)";
//            JDBC_Helper.updateTongQuat(sql, nv.getManv(), nv.getTentk(), nv.getMk(), nv.getHoten(), nv.getGioitinh(), nv.getNgaysinh(), nv.getSdt(), nv.getDiachi(), nv.getIdcv(), nv.getTrangthai());
//            return true;
//        }
//    }
//
//    public int update(String id, NhanVienModel nv) {
//        String sql = "update NhanVien set Manv = ?, taiKhoan = ?, matKhau = ?, hoTen = ?, gioiTinh = ?, ngaySinh =?, SDT = ?, diaChi = ?, IDCV = ?, trangThai = ? where Id = ? ";
//        return JDBC_Helper.updateTongQuat(sql, nv.getManv(), nv.getTentk(), nv.getMk(), nv.getHoten(), nv.getGioitinh(), nv.getNgaysinh(), nv.getSdt(), nv.getDiachi(), nv.getIdcv(), nv.getTrangthai(), id);
//
//    }
//
//    public int delete(String id) {
//        String sql = "delete from NhanVien where Id = ?";
//        return JDBC_Helper.updateTongQuat(sql, id);
//    }
//
//    public List<NhanVienViewModel> timkiem(String ma, String ten) {
//        List<NhanVienViewModel> nv = new ArrayList<>();
//        String sql = "select nv.Id, nv.Manv, nv.taiKhoan, nv.matKhau, nv.hoTen, nv.gioiTinh, nv.ngaySinh, nv.SDT, nv.diaChi,cv.Ten, nv.trangThai  from NhanVien nv join ChucVu cv on nv.IDCV = cv.Id where nv.MANV like '%" + ma + "' or nv.MANV like '" + ma + "%' or nv.MANV like '%" + ma + "%' or nv.HoTen like N'" + ten + "%' or nv.HoTen like N'%" + ten + "' or nv.HoTen like N'%" + ten + "%'";
//        ResultSet rs = JDBC_Helper.selectTongQuat(sql);
//        try {
//            while (rs.next()) {
//                String id = rs.getString(1);
//                String manv = rs.getString(2);
//                String tk = rs.getString(3);
//                String mk = rs.getString(4);
//                String hoten = rs.getString(5);
//                String gt = rs.getString(6);
//                String ns = rs.getString(7);
//                String sdt = rs.getString(8);
//                String diachi = rs.getString(9);
//                String tencv = rs.getString(10);
//                int trangthai = rs.getInt(11);
//                NhanVienViewModel nv1 = new NhanVienViewModel(id, manv, tk, mk, hoten, gt, ns, sdt, diachi, tencv, trangthai);
//                nv.add(nv1);
//            }
//            return nv;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
