package Repositories;

import DomainModels.KhachHang;
import ViewModels.KhachHangViewModel;
import java.util.List;

public interface KhachHangRepository {

    List<KhachHangViewModel> getAll();

    boolean add(KhachHang kh);

    boolean update(String maKh, KhachHang kh);

    boolean delete(String maKH);
    
    KhachHangViewModel getBySdt(String sdt);

}
