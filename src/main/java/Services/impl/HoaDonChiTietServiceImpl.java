/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.HoaDonChiTiet;
import Repositories.impl.HoaDonChiTietRepositoryImpl;
import Services.HoaDonChiTietService;
import java.util.List;
import Repositories.HoaDonChiTietRepository;

/**
 *
 * @author admin
 */
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    public HoaDonChiTietRepository hdctRepo = new HoaDonChiTietRepositoryImpl();

    @Override
    public int insert(HoaDonChiTiet hdct) {
        return hdctRepo.insert(hdct);
    }

    @Override
    public int delete(int IDHD) {
        return hdctRepo.delete(IDHD);
    }

    @Override
    public List<HoaDonChiTiet> selectByID(int idHoaDon) {
        return hdctRepo.selectByID(idHoaDon);
    }

    @Override
    public int deleteOne(int idHDCT) {
        return hdctRepo.deleteOne(idHDCT);
    }

}
