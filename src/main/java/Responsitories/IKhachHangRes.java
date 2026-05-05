package Responsitories;

import DomainModels.KhachHang;
import ViewModels.KhachHangViewModel;
import java.util.List;

public interface IKhachHangRes {

    List<KhachHangViewModel> getAll();

    boolean add(KhachHang kh);

    boolean update(String maKh, KhachHang kh);

    boolean delete(String maKH);

}
