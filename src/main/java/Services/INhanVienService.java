package Services;

import DomainModels.NhanVien;
import ViewModels.NhanVienViewModel;
import java.util.List;

public interface INhanVienService {

    public List<NhanVienViewModel> getAll();

    NhanVien getOne(String taiKhoan);

    public int add(NhanVien nv);

    public int update(String ma_nhan_vien, NhanVien nv);

    public int delete(String ma_nhan_vien);
}
