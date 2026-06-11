/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.HoaDon;
import Repositories.impl.HoaDonRepositoryImpl;
import Services.HoaDonService;
import java.util.List;
import Repositories.HoaDonRepository;

/**
 *
 * @author admin
 */
public class HoaDonServiceImpl implements HoaDonService{
        private HoaDonRepository hoaDonRep = new HoaDonRepositoryImpl();
    
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
