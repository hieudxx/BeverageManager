/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.SanPham;
import Services.SanPhamServices;
import ViewModels.SanPhamResponse;
import Responsitories.SanPhamResponsitories;
import Responsitories.impl.SanPhamResponsitoriesImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SanPhamServicesImpl implements SanPhamServices {

//        private SanPhamRepository1 sanPhamRep = new SanPhamRepositoryImpl1();
//    @Override
//    public List<SanPhamResponse1> getALL() {
//        return sanPhamRep.getALL();
//    }
    private SanPhamResponsitories spRep = new SanPhamResponsitoriesImpl();

    @Override
    public List<SanPhamResponse> getAll() {
        return spRep.getAll();
    }

    @Override
    public SanPhamResponse getOne(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(SanPham sp) {
        return spRep.add(sp);
    }

    @Override
    public boolean update(SanPham sp) {
        return spRep.update(sp);
    }

    @Override
    public boolean delete(String maSP) {
        return spRep.delete(maSP);
    }

    @Override
    public List<SanPhamResponse> search(String keyword) {
        List<SanPhamResponse> listSP = spRep.getAll();
        List<SanPhamResponse> resultSP = new ArrayList<>();

        for (SanPhamResponse sp : listSP) {
            if (sp.getMaSanPham().toLowerCase().contains(keyword.toLowerCase())) {
                SanPhamResponse spr = new SanPhamResponse();
                spr.setMaSanPham(sp.getMaSanPham());
                spr.setTenSanPham(sp.getTenSanPham());
                resultSP.add(spr);
            }
        }
        return resultSP;
    }
}
