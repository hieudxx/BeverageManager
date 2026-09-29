/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import ViewModels.ThongKeSanPhamViewModel;
import ViewModels.ThongKeTongQuanViewModel;
import java.time.LocalDateTime;
import java.util.List;

public interface ThongKeService {
    
    ThongKeTongQuanViewModel getTongQuan(LocalDateTime tuNgay, LocalDateTime denNgay);

    List<ThongKeSanPhamViewModel> getChiTietTheoSanPham(LocalDateTime tuNgay, LocalDateTime denNgay);

    ThongKeTongQuanViewModel getMonBanChayNhat(LocalDateTime tuNgay, LocalDateTime denNgay);
}
