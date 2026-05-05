package Services.impl;

import DomainModels.NhanVien;
import Responsitories.INhanVienRes;
import Responsitories.impl.NhanVienResImpl;
import Services.INhanVienService;
import ViewModels.NhanVienViewModel;
import java.util.List;

public class NhanVienServiceImpl implements INhanVienService {

    private final INhanVienRes nvRes = new NhanVienResImpl();

    @Override
    public List<NhanVienViewModel> getAll() {
        return nvRes.getAll();
    }

    @Override
    public NhanVien getOne(String taiKhoan) {
        return nvRes.getOne(taiKhoan);
    }

    @Override
    public int add(NhanVien nv) {
        boolean check = this.nvRes.add(nv);
        if (check) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int update(String id, NhanVien nv) {
        return nvRes.update(id, nv);
    }

    @Override
    public int delete(String id) {
        return nvRes.delete(id);
    }
}
