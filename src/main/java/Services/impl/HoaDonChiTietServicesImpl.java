/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.HoaDonChiTiet;
import Responsitories.HoaDonChiTietResponsitories;
import Responsitories.impl.HoaDonChiTietReponsitoriesImpl;
import Services.HoaDonChiTietService;
import java.util.List;

/**
 *
 * @author admin
 */
public class HoaDonChiTietServicesImpl implements HoaDonChiTietService{
    
    public HoaDonChiTietResponsitories hdctRepo=new HoaDonChiTietReponsitoriesImpl();

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
    public List<HoaDonChiTiet> getSpByID(int idsanpham) {
        return hdctRepo.getSpByID(idsanpham);
    }

    @Override
    public List<HoaDonChiTiet> getAll() {
        return hdctRepo.getAll();
    }

    @Override
    public int deleteOne(int idHDCT) {
        return hdctRepo.deleteOne(idHDCT);
    }
    
}
