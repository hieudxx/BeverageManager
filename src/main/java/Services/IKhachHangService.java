package Services;

import DomainModels.KhachHang;
import ViewModels.KhachHangViewModel;
import java.util.List;

public interface IKhachHangService {

    public List<KhachHangViewModel> getAll();
    
//    NhanVien getOne(String taiKhoan);

    public int add(KhachHang kh);

    public int update(String ma_kh, KhachHang kh);

    public int delete(String ma_kh);
}
