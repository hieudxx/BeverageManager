/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import DomainModels.HoaDon;
import DomainModels.HoaDonChiTiet;
import DomainModels.SanPham;
import DomainModels.Size;
import Utilities.JDBC_Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Repositories.HoaDonChiTietRepository;

/**
 *
 * @author admin
 */
public class HoaDonChiTietRepositoryImpl implements HoaDonChiTietRepository {

    @Override
    public List<HoaDonChiTiet> selectByID(int idHoaDon) {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();

        String query = "SELECT hdct.id, hdct.id_hoa_don, hdct.id_size, "
                + "sp.ten_san_pham, ssp.ten_size, hdct.so_luong, hdct.gia_luc_ban "
                + "FROM HoaDonChiTiet hdct "
                + "JOIN SizeSanPham ssp ON hdct.id_size = ssp.id "
                + "JOIN SanPham sp ON ssp.id_san_pham = sp.id "
                + "WHERE hdct.id_hoa_don = ?";

        ResultSet rs = JDBC_Helper.selectTongQuat(query, idHoaDon);
        try {
            while (rs.next()) {

                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setId(rs.getInt("id"));
                hdct.setSoLuong(rs.getInt("so_luong"));
                hdct.setGiaLucBan(rs.getBigDecimal("gia_luc_ban"));

                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("id_hoa_don"));
                hdct.setHoaDon(hd);

                Size ssp = new Size();
                ssp.setId(rs.getInt("id_size"));
                ssp.setTenSize(rs.getString("ten_size"));

                SanPham sp = new SanPham();
                sp.setTenSanPham(rs.getString("ten_san_pham"));

                ssp.setSanPham(sp);
                hdct.setSize(ssp);

                listHDCT.add(hdct);
            }
            return listHDCT;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public int insert(HoaDonChiTiet hdct) {
        String checkQuery = "SELECT id FROM HoaDonChiTiet WHERE id_hoa_don = ? AND id_size = ?";
        ResultSet rs = JDBC_Helper.selectTongQuat(checkQuery,
                hdct.getHoaDon().getId(),
                hdct.getSize().getId());

        try {
            if (rs.next()) {
                String updateQuery = "UPDATE HoaDonChiTiet SET so_luong = so_luong + ? WHERE id = ?";
                return JDBC_Helper.updateTongQuat(updateQuery, hdct.getSoLuong(), rs.getInt("id"));
            } else {
                String insertQuery = "INSERT INTO HoaDonChiTiet (id_hoa_don, id_size, so_luong, gia_luc_ban) VALUES (?, ?, ?, ?)";
                return JDBC_Helper.updateTongQuat(insertQuery,
                        hdct.getHoaDon().getId(),
                        hdct.getSize().getId(),
                        hdct.getSoLuong(),
                        hdct.getGiaLucBan());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(int IDHD) {
        String query = "DELETE FROM [dbo].[HoaDonChiTiet] WHERE id_hoa_don = ?";
        return JDBC_Helper.updateTongQuat(query, IDHD);
    }

    @Override
    public int deleteOne(int idHDCT) {
        String query = "DELETE FROM HoaDonChiTiet WHERE id = ?";
        return JDBC_Helper.updateTongQuat(query, idHDCT);
    }

}
