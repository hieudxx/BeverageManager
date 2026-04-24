/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.HoaDon;
import Responsitories.HoaDonResponsitories;
import Responsitories.impl.HoaDonResponsitoriesImpl;
import Services.HoaDonService;
import java.util.List;

/**
 *
 * @author admin
 */
public class HoaDonServiceImpl implements HoaDonService{
        private HoaDonResponsitories hoaDonRep = new HoaDonResponsitoriesImpl();
    
    @Override
    public List<HoaDon> selectByHDChoTT() {
        return hoaDonRep.selectByHDChoTT();
    }
    
    @Override
    public HoaDon selectByMaHD(String maHD) {
        return hoaDonRep.selectByMaHD(maHD);
    }

    @Override
    public int insert(HoaDon hd) {
        return hoaDonRep.insert(hd);
    }

    @Override
    public int update(HoaDon hd) {
        return hoaDonRep.update(hd);
    }

    @Override
    public int updateNoKH(HoaDon hd) {
        return hoaDonRep.updateNoKH(hd);
        }

    
}
