/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.NhanVien;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface INhanVienService {

    List<NhanVien> getALL();

    NhanVien getOne(String TaiKhoan);
}
