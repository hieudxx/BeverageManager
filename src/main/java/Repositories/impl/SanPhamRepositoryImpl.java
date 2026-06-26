/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import DomainModels.DanhMuc;
import DomainModels.SanPham;
import Utilities.JDBC_Helper;
import ViewModels.SanPhamViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Repositories.SanPhamRepository;

/**
 *
 * @author admin
 */
public class SanPhamRepositoryImpl implements SanPhamRepository {

    @Override
    public List<SanPhamViewModel> getAll() {
        List<SanPhamViewModel> listSP = new ArrayList<>();
        String query = "select sp.id,ma_san_pham, ten_san_pham, gia_co_ban, hinh_anh,dang_ban,sp.trang_thai_hien_thi, dm.ten_danh_muc  \n"
                + "from SanPham sp join DanhMuc dm on sp.id_danh_muc=dm.id ";
        ResultSet rs = JDBC_Helper.selectTongQuat(query);
        try {
            while (rs.next()) {
                SanPhamViewModel sp = new SanPhamViewModel();
                sp.setId(rs.getInt("id"));
                sp.setMaSanPham(rs.getString("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setGiaCoBan(rs.getBigDecimal("gia_co_ban"));
                sp.setHinhAnh(rs.getString("hinh_anh"));
                sp.setDangBan(rs.getBoolean("dang_ban"));
                sp.setTrangThaiHienThi(rs.getBoolean("trang_thai_hien_thi"));
                sp.setTenDanhMuc(rs.getString("ten_danh_muc"));
                listSP.add(sp);
            }
            return listSP;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public SanPhamViewModel getOne(String ma) {
        String query = "SELECT sp.id, ma_san_pham, ten_san_pham, gia_co_ban, hinh_anh, dang_ban, sp.trang_thai_hien_thi, dm.ten_danh_muc "
                + "FROM SanPham sp JOIN DanhMuc dm ON sp.id_danh_muc = dm.id "
                + "WHERE ma_san_pham = ?";

        ResultSet rs = JDBC_Helper.selectTongQuat(query, ma);

        try {
            if (rs.next()) {
                SanPhamViewModel sp = new SanPhamViewModel();

                sp.setId(rs.getInt("id"));
                sp.setMaSanPham(rs.getString("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setGiaCoBan(rs.getBigDecimal("gia_co_ban"));
                sp.setHinhAnh(rs.getString("hinh_anh"));
                sp.setDangBan(rs.getBoolean("dang_ban"));
                sp.setTrangThaiHienThi(rs.getBoolean("trang_thai_hien_thi"));
                sp.setTenDanhMuc(rs.getString("ten_danh_muc"));

                return sp;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean check_trung_ma(String ma) {
        if (getOne(ma) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean add(SanPham sp) {
        if (check_trung_ma(sp.getMaSanPham())) {
            JOptionPane.showMessageDialog(null, "Đã tồn tại sản phẩm");
            return false;
        } else {
            String sql = "INSERT INTO SanPham(ma_san_pham, id_danh_muc, ten_san_pham, gia_co_ban, hinh_anh, dang_ban, trang_thai_hien_thi)\n"
                    + "VALUES (?,?,?,?,?,?,?)";

            JDBC_Helper.updateTongQuat(
                    sql,
                    sp.getMaSanPham(),
                    sp.getDanhMuc().getId(),
                    sp.getTenSanPham(),
                    sp.getGiaCoBan(),
                    sp.getHinhAnh(),
                    sp.isDangBan(),
                    sp.isTrangThaiHienThi()
            );
            return true;
        }
    }

    @Override
    public boolean update(SanPham sp) {
        try{
            String sql = "UPDATE SanPham SET id_danh_muc=?, ten_san_pham=?, gia_co_ban=?, hinh_anh=?, dang_ban=?, trang_thai_hien_thi=? WHERE ma_san_pham=?";

            int result = JDBC_Helper.updateTongQuat(
                    sql,
                    sp.getDanhMuc().getId(),
                    sp.getTenSanPham(),
                    sp.getGiaCoBan(),
                    sp.getHinhAnh(),
                    sp.isDangBan(),
                    sp.isTrangThaiHienThi(),
                    sp.getMaSanPham()
            );
            return result > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(String maSP){
        try{
            String sql = "DELETE FROM SanPham WHERE ma_san_pham=?";
            int result = JDBC_Helper.updateTongQuat(sql, maSP);
            return result > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
