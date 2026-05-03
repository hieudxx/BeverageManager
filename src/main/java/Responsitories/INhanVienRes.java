
package Responsitories;

import DomainModels.NhanVien;
import ViewModels.NhanVienViewModel;
import java.util.List;

public interface INhanVienRes {

    List<NhanVienViewModel> getAll();

    NhanVien getOne(String taiKhoan);

    boolean add(NhanVien nv);

    int update(String ma_nhan_vien, NhanVien nv);

    int delete(String ma_nhan_vien);

    public NhanVien getByTenDangNhap(String tenDangNhap);
//    public List<NhanVienViewModel> find(String maNv, String hoTen);
}
