/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import Repositories.ThongKeRepository;
import Repositories.impl.ThongKeRepositoryImpl;
import Services.ThongKeService;
import ViewModels.ThongKeSanPhamViewModel;
import ViewModels.ThongKeTongQuanViewModel;
import java.time.LocalDateTime;
import java.util.List;

public class ThongKeServiceImpl implements ThongKeService{
        private final ThongKeRepository thongKeRepository = new ThongKeRepositoryImpl();

    @Override
    public ThongKeTongQuanViewModel getTongQuan(LocalDateTime tuNgay, LocalDateTime denNgay) {
        return thongKeRepository.getTongQuan(tuNgay, denNgay);
    }

    @Override
    public List<ThongKeSanPhamViewModel> getChiTietTheoSanPham(LocalDateTime tuNgay, LocalDateTime denNgay) {
        return thongKeRepository.getChiTietTheoSanPham(tuNgay, denNgay);
    }

    @Override
    public ThongKeTongQuanViewModel getMonBanChayNhat(LocalDateTime tuNgay, LocalDateTime denNgay) {
        return thongKeRepository.getMonBanChayNhat(tuNgay, denNgay);
    }
}
