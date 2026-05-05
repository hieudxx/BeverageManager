package Services.impl;

import DomainModels.KhachHang;
import Responsitories.IKhachHangRes;
import Responsitories.impl.KhachHangResImpl;
import Services.IKhachHangService;
import ViewModels.KhachHangViewModel;
import java.util.List;

public class KhachHangServicesImpl implements IKhachHangService {

    private final IKhachHangRes khRes = new KhachHangResImpl();

    @Override
    public List<KhachHangViewModel> getAll() {
        return khRes.getAll();
    }

    @Override
    public boolean add(KhachHang kh) {
        return khRes.add(kh);
    }

    @Override
    public boolean update(String ma_kh, KhachHang kh) {
        return khRes.update(ma_kh, kh);
    }

    @Override
    public boolean delete(String ma_kh) {
        return khRes.delete(ma_kh);

    }

}
