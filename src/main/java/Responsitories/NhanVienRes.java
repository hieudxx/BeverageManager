package Responsitories;

import DomainModels.NhanVien;
import Utilities.JDBC_Helper;
import ViewModels.NhanVienViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class NhanVienRes {

    public List<NhanVienViewModel> getAll() {
        List<NhanVienViewModel> listNV = new ArrayList<>();
        String sql = "select id, ma_nhan_vien, ten_dang_nhap, mat_khau, ho_ten, vai_tro, trang_thai_lam_viec FROM NhanVien";
        try {
            ResultSet rs = JDBC_Helper.selectTongQuat(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String maNhanVien = rs.getString(2);
                String tenDangNhap = rs.getString(3);
                String matKhau = rs.getString(4);
                String hoTen = rs.getString(5);
                String vaiTro = rs.getString(6);
                boolean trangThai = rs.getBoolean(7);
                NhanVienViewModel nv = new NhanVienViewModel(id, maNhanVien, tenDangNhap, matKhau, hoTen, vaiTro, trangThai ? "Đang làm" : "Đã nghỉ");
                listNV.add(nv);
            }
            return listNV;
        } catch (SQLException e) {
            return null;
        }
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

    public NhanVienViewModel getByMaNv(String maNhanVien) {
        NhanVienViewModel nv = null;
        String sql = "select nv.id, nv.ma_nhan_vien, nv.ten_dang_nhap, nv.mat_khau, nv.ho_ten, nv.vai_tro, nv.trang_thai_lam_viec from NhanVien nv where nv.ma_nhan_vien = ?";
        try {

            ResultSet rs = JDBC_Helper.selectTongQuat(sql, maNhanVien);
            while (rs.next()) {
                int id = rs.getInt(1);
                String maNv = rs.getString(2);
                String tenDangNhap = rs.getString(3);
                String matKhau = rs.getString(4);
                String hoTen = rs.getString(5);
                String vaiTro = rs.getString(6);
                boolean trangThai = rs.getBoolean(7);
                nv = new NhanVienViewModel(id, maNv, tenDangNhap, matKhau, hoTen, vaiTro, trangThai ? "Đang làm" : "Đã nghỉ");

            }
            return nv;
        } catch (SQLException e) {
            return null;
        }
    }

    public NhanVien getByTenDangNhap(String tenDangNhap) {
        NhanVien nv = null;
        String sql = "select id, ma_nhan_vien, ten_dang_nhap, mat_khau, ho_ten, vai_tro, trang_thai_lam_viec from NhanVien where ten_dang_nhap = ?";
        try {

            ResultSet rs = JDBC_Helper.selectTongQuat(sql, tenDangNhap);
            while (rs.next()) {
                int id = rs.getInt(1);
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

    public int update(String id, NhanVien nv) {
        String sql = "update NhanVien set ten_dang_nhap = ?, ho_ten = ?, vai_tro = ?, trang_thai_lam_viec =? where ma_nhan_vien = ? ";
        return JDBC_Helper.updateTongQuat(sql, nv.getTenDangNhap(), nv.getHoTen(), nv.getVaiTro(), nv.isTrangThai(), id);
    }

    public int delete(String id) {
        String sql = "delete from NhanVien where ma_nhan_vien = ?";
        return JDBC_Helper.updateTongQuat(sql, id);
    }

    public List<NhanVienViewModel> findByName(String name) {
        List<NhanVienViewModel> listNv = new ArrayList<>();
        String sql = "select id, ma_nhan_vien, ten_dang_nhap, ho_ten, vai_tro, trang_thai_lam_viec"
                + " from NhanVien nv where ho_ten like N'" + name + "%' or ho_ten like N'%" + name + "' or ho_ten like N'%" + name + "%'";
        ResultSet rs = JDBC_Helper.selectTongQuat(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                String maNv = rs.getString(2);
                String tenDangNhap = rs.getString(3);
                String matKhau = rs.getString(4);
                String hoTen = rs.getString(5);
                String vaiTro = rs.getString(6);
                boolean trangThai = rs.getBoolean(7);
                NhanVienViewModel nv = new NhanVienViewModel(id, maNv, tenDangNhap, matKhau, hoTen, vaiTro, trangThai ? "Đang làm" : "Đã nghỉ");
                listNv.add(nv);
            }
            return listNv;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<NhanVienViewModel> findByManv(String MaNv) {
        List<NhanVienViewModel> listNv = new ArrayList<>();
        String sql = "select id, ma_nhan_vien, ten_dang_nhap, ho_ten, vai_tro, trang_thai_lam_viec"
                + " from NhanVien nv where ma_nhan_vien like N'" + MaNv + "%' or ma_nhan_vien like N'%" + MaNv + "' or ma_nhan_vien like N'%" + MaNv + "%'";
        ResultSet rs = JDBC_Helper.selectTongQuat(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                String maNv = rs.getString(2);
                String tenDangNhap = rs.getString(3);
                String matKhau = rs.getString(4);
                String hoTen = rs.getString(5);
                String vaiTro = rs.getString(6);
                boolean trangThai = rs.getBoolean(7);
                NhanVienViewModel nv = new NhanVienViewModel(id, maNv, tenDangNhap, matKhau, hoTen, vaiTro, trangThai ? "Đang làm" : "Đã nghỉ");
                listNv.add(nv);
            }
            return listNv;
        } catch (SQLException e) {
            return null;
        }
    }

    public NhanVien findByMaNv(String maNhanVien) {
        NhanVien nv = null;
        String sql = "select id, ma_nhan_vien, ten_dang_nhap, ho_ten, vai_tro, trang_thai_lam_viec from NhanVien where ma_nhan_vien = ?";
        try {

            ResultSet rs = JDBC_Helper.selectTongQuat(sql, maNhanVien);
            while (rs.next()) {
                int id = rs.getInt(1);
                String maNv = rs.getString(2);
                String tenDangNhap = rs.getString(3);
                String matKhau = rs.getString(4);
                String hoTen = rs.getString(5);
                String vaiTro = rs.getString(6);
                boolean trangThai = rs.getBoolean(7);
                nv = new NhanVien(id, maNv, tenDangNhap, matKhau, hoTen, vaiTro, trangThai);

            }
            return nv;
        } catch (SQLException e) {
            return null;
        }
    }

}
