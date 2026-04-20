/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModels.NhanVien;
import ViewModels.NhanVienViewModel;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface INhanVienUIService {
    public List<NhanVienViewModel> getAll();
    public NhanVienViewModel getByMaNv(String maNhanVien);
    public NhanVien getByTenDangNhap(String tenDangNhap);
    public int add(NhanVien nv);
    public int update(String id, NhanVien nv);
    public int delete(String id);
    public List<NhanVienViewModel> find(String maNv, String hoTen);
}
