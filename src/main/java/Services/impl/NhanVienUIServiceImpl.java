/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.NhanVien;
import Responsitories.NhanVienRes;
import Services.INhanVienUIService;
import ViewModels.NhanVienViewModel;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanVienUIServiceImpl implements INhanVienUIService {

    NhanVienRes nvRes = new NhanVienRes();

    @Override
    public List<NhanVienViewModel> getAll() {
        return nvRes.getAll();
    }

    @Override
    public NhanVienViewModel getByMaNv(String maNhanVien) {
        return nvRes.getByMaNv(maNhanVien);
    }

    @Override
    public NhanVien getByTenDangNhap(String tenDangNhap) {
        return nvRes.getByTenDangNhap(tenDangNhap);
    }

    @Override
    public int add(NhanVien nv) {
        boolean check = this.nvRes.add(nv);
        if (check) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int update(String id, NhanVien nv) {
        return nvRes.update(id, nv);
    }

    @Override
    public int delete(String id) {
        return nvRes.delete(id);
    }

//    @Override
//    public List<NhanVienViewModel> find(String maNv, String hoTen) {
//        return nvRes.find(maNv);
//    }

    @Override
    public List<NhanVienViewModel> find(String maNv, String hoTen) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
