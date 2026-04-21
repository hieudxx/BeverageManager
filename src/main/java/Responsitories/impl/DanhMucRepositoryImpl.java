/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import DomainModels.DanhMuc;
import Responsitories.DanhMucRepository;
import Utilities.DBConnect;
import Utilities.JDBC_Helper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DanhMucRepositoryImpl implements DanhMucRepository{
    // Câu lệnh Sql
    private static final String GET_ALL_SQL =
        "SELECT [id], [ma_Danh_Muc], [ten_Danh_Muc], [trang_Thai_Hien_Thi] FROM [dbo].[DanhMuc]";
    private static final String INSERT_SQL =
        "INSERT INTO DanhMuc(ma_Danh_Muc, ten_Danh_Muc, "+" trang_Thai_Hien_Thi) values(?,?,?)";
    private static final String UPDATE_SQL =
        "UPDATE DanhMuc SET ten_Danh_Muc = ?, trang_Thai_Hien_Thi = ? WHERE ma_Danh_Muc = ?";
    private static final String DELETE_SQL =
        "DELETE FROM DanhMuc WHERE ma_Danh_Muc = ?";
    
    // List
    @Override
    public List<DanhMuc> getAll(){
        List<DanhMuc> listDanhMuc = new ArrayList<>();
        ResultSet rs = JDBC_Helper.selectTongQuat(GET_ALL_SQL);
        try {
            while(rs.next()){
                DanhMuc dm = new DanhMuc();
                dm.setId(rs.getInt("id"));
                dm.setMaDanhMuc(rs.getString("ma_Danh_Muc"));
                dm.setTenDanhMuc(rs.getString("ten_Danh_Muc"));
                dm.setTrangThaiHienThi(rs.getBoolean("trang_Thai_Hien_Thi"));
                listDanhMuc.add(dm);
            }
            return listDanhMuc;
        } catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    // Add
    @Override
    public boolean add(DanhMuc dm){
        try(
            Connection con = DBConnect.getConnect();
            PreparedStatement ps = con.prepareStatement(INSERT_SQL);
        ){
            ps.setObject(1, dm.getMaDanhMuc());
            ps.setObject(2, dm.getTenDanhMuc());
            ps.setObject(3, dm.isTrangThaiHienThi());
            
            // Thực thi câu lệnh INSERT
            // executeUpdate() trả về số dòng bị ảnh hưởng
            // > 0 nghĩa là insert thành công
            return ps.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    // Update
    public boolean update(DanhMuc dm, String maDM){
        try(
            Connection con = DBConnect.getConnect();
            PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
        ){
            ps.setObject(1, dm.getTenDanhMuc());
            ps.setObject(2, dm.isTrangThaiHienThi());
            ps.setObject(3, maDM);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    // Delete
    public boolean delete(String maDM){
        try(
           Connection con = DBConnect.getConnect();
           PreparedStatement ps = con.prepareStatement(DELETE_SQL);
        ){
            ps.setObject(1, maDM);
            return ps.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
