
package Responsitories;

import DomainModels.NhanVien;
import ViewModels.NhanVienViewModel;
import java.util.List;

public interface INhanVienRes {

    List<NhanVienViewModel> getAll();

    NhanVien getOne(String taiKhoan);

    boolean add(NhanVien nv);

    int update(String id, NhanVien nv);

    int delete(String id);

    public NhanVien getByTenDangNhap(String tenDangNhap);
//    public List<NhanVienViewModel> find(String maNv, String hoTen);
}
