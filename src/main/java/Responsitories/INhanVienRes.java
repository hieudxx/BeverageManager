/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Responsitories;

import DomainModels.NhanVien;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface INhanVienRes {
    List<NhanVien> getALL();
    
    NhanVien getOne(String TaiKhoan);
    
    boolean insert(NhanVien nv);
    boolean update(NhanVien nv);
    boolean delete(String maNhanVien);
    
}
