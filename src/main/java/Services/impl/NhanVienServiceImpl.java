/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.NhanVien;
import Responsitories.NhanVienRes;
import Responsitories.impl.NhanVienResImpl;
import Services.INhanVienService;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanVienServiceImpl implements INhanVienService{
    private INhanVienService nv = (INhanVienService) new NhanVienResImpl();
    @Override
    public List<NhanVien> getALL() {
        return nv.getALL();
    }

    @Override
    public NhanVien getOne(String TaiKhoan) {
        return nv.getOne(TaiKhoan);
    }
}
