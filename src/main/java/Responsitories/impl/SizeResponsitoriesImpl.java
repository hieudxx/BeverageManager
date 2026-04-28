/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Responsitories.impl;

import DomainModels.SanPham;
import DomainModels.Size;
import Responsitories.SizeResponsitories;
import Utilities.DBConnect;
import Utilities.JDBC_Helper;
import ViewModels.SizeViewModel;

import DomainModels.Size;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

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
            = "INSERT INTO SizeSanPham(ma_size, id_san_pham, ten_size, gia_chenh_lech, trang_thai_hien_thi) values (?,?,?,?,?)";
    private static final String UPDATE_SQL
            = "UPDATE SizeSanPham SET ma_size=?, id_san_pham=?, ten_size=?, gia_chenh_lech=?, trang_thai_hien_thi=? WHERE ma_size=?";
    private static final String DELETE_SQL
            = "DELETE FROM SizeSanPham WHERE ma_size=?";

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
                size.setTenSize(rs.getString("ten_size"));
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

    public boolean add(Size s) {
        try (
                Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(INSERT_SQL);) {
            ps.setString(1, s.getMaSize());
            ps.setInt(2, s.getSanPham().getId());
            ps.setString(3, s.getTenSize());
            ps.setBigDecimal(4, s.getGiaChenhLech());
            ps.setBoolean(5, s.isTrangThaiHienThi());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

// Update
    public boolean update(Size s, String maS) {
        try (
                Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(UPDATE_SQL);) {
            ps.setString(1, s.getMaSize());
            ps.setInt(2, s.getSanPham().getId());
            ps.setString(3, s.getTenSize());
            ps.setBigDecimal(4, s.getGiaChenhLech());
            ps.setBoolean(5, s.isTrangThaiHienThi());

            ps.setString(6, maS);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

// Delete
    public boolean delete(String maS) {
        try (
                Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(DELETE_SQL);) {
            ps.setString(1, maS);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
