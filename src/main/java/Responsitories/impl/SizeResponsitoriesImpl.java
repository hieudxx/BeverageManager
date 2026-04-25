/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import DomainModels.SanPham;
import Responsitories.SizeResponsitories;
import Utilities.JDBC_Helper;
import ViewModels.SizeViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class SizeResponsitoriesImpl implements SizeResponsitories {

    // Câu lệnh SQL
    private static final String GET_ALL_SQL
            = "SELECT s.id, s.ma_size, s.id_san_pham, s.ten_size, s.gia_chenh_lech, s.trang_thai_hien_thi, "
            + "sp.ma_san_pham, sp.ten_san_pham "
            + "FROM SizeSanPham s "
            + "JOIN SanPham sp ON s.id_san_pham = sp.id";
    private static final String INSERT_SQL
            = "INSERT INTO Size(ma_size, id_san_pham, ten_size, gia_chenh_lech, trang_thai_hien_thi) values (?,?,?,?,?)";

    // List
    @Override
    public List<SizeViewModel> getAll() {
        List<SizeViewModel> ListSize = new ArrayList<>();
        ResultSet rs = JDBC_Helper.selectTongQuat(GET_ALL_SQL);
        try {
            while (rs.next()) {
                SizeViewModel size = new SizeViewModel();
                size.setId(rs.getInt("id"));
                size.setMaSize(rs.getString("ma_size"));
                size.setTenSizel(rs.getString("ten_size"));
                size.setGiaChenhLech(rs.getBigDecimal("gia_chenh_lech"));
                size.setTrangThaiHienThi(rs.getBoolean("trang_thai_hien_thi"));
                
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("id_san_pham"));
                sp.setMaSanPham(rs.getString("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
               
                size.setSanPham(sp);
                ListSize.add(size);
            }
            return ListSize;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // Add
    @Override
    public boolean add(Size s){
        
    }
}
