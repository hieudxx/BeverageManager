/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import Responsitories.SanPhamResponsitories;
import Utilities.JDBC_Helper;
import ViewModels.SanPhamResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SanPhamResponsitoriesImpl implements SanPhamResponsitories{
    

    @Override
    public List<SanPhamResponse> getAll() {
        List<SanPhamResponse> listSP=new ArrayList<>();
        String query="select sp.id,ma_san_pham, ten_san_pham, gia_co_ban, hinh_anh,dang_ban,sp.trang_thai_hien_thi, dm.ten_danh_muc  \n" +
        "from SanPham sp join DanhMuc dm on sp.id_danh_muc=dm.id ";
        ResultSet rs= JDBC_Helper.selectTongQuat(query);
        try{
            while (rs.next()){
                SanPhamResponse sp=new SanPhamResponse();
                sp.setId(rs.getInt("id"));
                sp.setMaSanPham(rs.getString("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setGiaCoBan(rs.getBigDecimal("gia_co_ban"));
                sp.setHinhAnh(rs.getString("hinh_anh"));
                sp.setDangBan(rs.getBoolean("dang_ban"));
                sp.setTrangThaiHienThi(rs.getBoolean("trang_thai_hien_thi"));
                listSP.add(sp);
            }
            return listSP;
        } 
        catch (SQLException ex) {
           ex.printStackTrace();
            return new ArrayList<>();
         }
    }

    @Override
    public SanPhamResponse getOne(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
