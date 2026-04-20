/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import DomainModels.DanhMuc;
import Responsitories.DanhMucRepository;
import Utilities.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class DanhMucRepositoryImpl implements DanhMucRepository{
    @Override
    public ArrayList<DanhMuc> getAllDanhMuc(){
        ArrayList<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT ma_danh_muc,ten_danh_muc,trang_thai_hien_thi FROM DanhMuc";
        
        try (
            Connection con = DBConnect.getConnect();
            PreparedStatement ps = con.prepareStatement(sql)
        ){
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                DanhMuc dm = new DanhMuc();
                dm.setMaDanhMuc(rs.getString("ma_danh_muc"));
                dm.setTenDanhMuc(rs.getString("ten_danh_muc"));
                dm.setTrangThaiHienThi(rs.getBoolean("trang_thai_hien_thi"));

                list.add(dm);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
}
