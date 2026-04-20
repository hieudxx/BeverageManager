/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import Services.SanPhamServices;
import ViewModels.SanPhamResponse;
import Responsitories.SanPhamResponsitories;
import Responsitories.impl.SanPhamResponsitoriesImpl;
import java.util.List;

/**
 *
 * @author admin
 */
public class SanPhamServicesImpl implements SanPhamServices{

    
//        private SanPhamRepository1 sanPhamRep = new SanPhamRepositoryImpl1();
//    @Override
//    public List<SanPhamResponse1> getALL() {
//        return sanPhamRep.getALL();
//    }
    
    private SanPhamResponsitories spRep=new SanPhamResponsitoriesImpl();
    
    @Override
    public List<SanPhamResponse> getAll() {
        return spRep.getAll();
    }

    @Override
    public SanPhamResponse getOne(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
