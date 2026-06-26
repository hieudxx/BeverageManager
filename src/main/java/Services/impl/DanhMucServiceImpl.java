/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.DanhMuc;
import Repositories.DanhMucRepository;
import Repositories.impl.DanhMucRepositoryImpl;
import ViewModels.DanhMucViewModel;
import java.util.ArrayList;
import java.util.List;
import Services.DanhMucService;

/**
 *
 * @author ADMIN
 */
public class DanhMucServiceImpl implements DanhMucService {

    private DanhMucRepository DanhMucRep = new DanhMucRepositoryImpl();

    @Override
    public List<DanhMuc> getAll() {
        return DanhMucRep.getAll();
    }

    @Override
    public boolean add(DanhMuc dm) {
        return DanhMucRep.add(dm);
    }

    @Override
    public boolean update(DanhMuc dm, String maDM) {
        return DanhMucRep.update(dm, maDM);
    }

    @Override
    public boolean delete(String maDM) {
        return DanhMucRep.delete(maDM);
    }

    @Override
    public List<DanhMucViewModel> getAllView() {
        List<DanhMuc> list = DanhMucRep.getAll();
        List<DanhMucViewModel> result = new ArrayList<>();

        for (DanhMuc dm : list) {
            DanhMucViewModel vm = new DanhMucViewModel();
            vm.setId(dm.getId());
            vm.setMaDanhMuc(dm.getMaDanhMuc());
            vm.setTenDanhMuc(dm.getTenDanhMuc());
            vm.setTrangThaiHienThi(dm.isTrangThaiHienThi());

            result.add(vm);
        }
        return result;

    }

    @Override
    public List<DanhMucViewModel> search(String keyword) {
        List<DanhMuc> listdm = DanhMucRep.getAll();
        List<DanhMucViewModel> resultdm = new ArrayList<>();

        for (DanhMuc dm : listdm) {
            if (dm.getMaDanhMuc().toLowerCase().contains(keyword.toLowerCase())) {

                DanhMucViewModel vm = new DanhMucViewModel();
                vm.setId(dm.getId());
                vm.setMaDanhMuc(dm.getMaDanhMuc());
                vm.setTenDanhMuc(dm.getTenDanhMuc());
                vm.setTrangThaiHienThi(dm.isTrangThaiHienThi());

                resultdm.add(vm);
            }
        }
        return resultdm;
    }

    @Override
    public List<DanhMucViewModel> getAllByTrangThai() {
        List<DanhMuc> list = DanhMucRep.getAllByTrangThai();
        List<DanhMucViewModel> result = new ArrayList<>();

        for (DanhMuc dm : list) {
            DanhMucViewModel vm = new DanhMucViewModel();
            vm.setId(dm.getId());
            vm.setMaDanhMuc(dm.getMaDanhMuc());
            vm.setTenDanhMuc(dm.getTenDanhMuc());
            vm.setTrangThaiHienThi(dm.isTrangThaiHienThi());
            result.add(vm);
        }
        return result;
    }
}
