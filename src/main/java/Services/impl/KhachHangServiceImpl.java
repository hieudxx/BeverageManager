package Services.impl;

import DomainModels.KhachHang;
import Repositories.impl.KhachHangResImpl;
import ViewModels.KhachHangViewModel;
import java.util.List;
import Repositories.KhachHangRepository;
import Services.KhachHangService;

public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khRes = new KhachHangResImpl();

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

    @Override
    public KhachHangViewModel getBySdt(String sdt) {
        return khRes.getBySdt(sdt);
    }

}
