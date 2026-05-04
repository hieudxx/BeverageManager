package Responsitories;

import DomainModels.KhachHang;
import ViewModels.KhachHangViewModel;
import java.util.List;


public interface IKhachHangRes {
    
    List<KhachHangViewModel> getAll();

    KhachHang getOne(String taiKhoan);

    boolean add(KhachHang kh);

    int update(String maKh, KhachHang kh);

    int delete(String maKH);
    
}
