/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;

import DomainModels.HoaDon;
import java.util.List;

/**
 *
 * @author admin
 */
public interface HoaDonRepository {
    
    int insert(HoaDon hd);
    
    int update(HoaDon hd);
    
    int updateNoKH(HoaDon hd);
    
    List<HoaDon> selectByHDChoTT();
    
    HoaDon selectByMaHD(String maHD);
}
