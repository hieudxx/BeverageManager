package Responsitories.impl;

import DomainModels.KhachHang;
import Responsitories.IKhachHangRes;
import Utilities.JDBC_Helper;
import ViewModels.KhachHangViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhachHangResImpl implements IKhachHangRes{

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
    public KhachHang getOne(String taiKhoan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(KhachHang kh) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(String maKh, KhachHang kh) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(String maKH) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
