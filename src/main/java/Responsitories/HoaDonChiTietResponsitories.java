/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Responsitories;

import DomainModels.HoaDonChiTiet;
import java.util.List;

/**
 *
 * @author admin
 */
public interface HoaDonChiTietResponsitories {
    
    int insert (HoaDonChiTiet hdct);
    
    int delete (String IDHD);
    
     List<HoaDonChiTiet> selectByID(String idHoaDon);
     List<HoaDonChiTiet> getSpByID(String idsanpham);
     List<HoaDonChiTiet> getAll();
    
}
