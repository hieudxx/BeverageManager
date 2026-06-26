/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.SanPham;
import ViewModels.SanPhamViewModel;
import Repositories.impl.SanPhamRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import Repositories.SanPhamRepository;
import Services.SanPhamService;

/**
 *
 * @author admin
 */
public class SanPhamServiceImpl implements SanPhamService {

    private SanPhamRepository spRep = new SanPhamRepositoryImpl();

    @Override
    public List<SanPhamViewModel> getAll() {
        return spRep.getAll();
    }

    @Override
    public SanPhamViewModel getOne(String ma) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public List<SanPhamViewModel> search(String keyword) {
        List<SanPhamViewModel> listSP = spRep.getAll();
        List<SanPhamViewModel> resultSP = new ArrayList<>();

        for (SanPhamViewModel sp : listSP) {
            if (sp.getMaSanPham().toLowerCase().contains(keyword.toLowerCase())) {
                SanPhamViewModel spr = new SanPhamViewModel();
                spr.setMaSanPham(sp.getMaSanPham());
                spr.setTenSanPham(sp.getTenSanPham());
                resultSP.add(spr);
            }
        }
        return resultSP;
    }
}
