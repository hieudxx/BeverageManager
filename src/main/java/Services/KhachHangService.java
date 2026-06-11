package Services;

import DomainModels.KhachHang;
import ViewModels.KhachHangViewModel;
import java.util.List;

public interface KhachHangService {

    public List<KhachHangViewModel> getAll();

    boolean add(KhachHang kh);

    boolean update(String ma_kh, KhachHang kh);

    boolean delete(String ma_kh);
    
    KhachHangViewModel getBySdt(String sdt);
}
