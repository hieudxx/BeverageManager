/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.HoaDonChiTiet;
import java.util.List;

/**
 *
 * @author admin
 */
public interface HoaDonChiTietService {
    
    int insert(HoaDonChiTiet hdct);
    
    int delete (int IDHD);
    
    List<HoaDonChiTiet> selectByID(int idHoaDon);
    
    List<HoaDonChiTiet> getSpByID(int idsanpham);
    
    List<HoaDonChiTiet> getAll();
}
