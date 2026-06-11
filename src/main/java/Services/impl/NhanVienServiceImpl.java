package Services.impl;

import DomainModels.NhanVien;
import Repositories.impl.NhanVienResImpl;
import ViewModels.NhanVienViewModel;
import java.util.List;
import Repositories.NhanVienRepository;
import Services.NhanVienService;

public class NhanVienServiceImpl implements NhanVienService {

    private final NhanVienRepository nvRes = new NhanVienResImpl();

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
