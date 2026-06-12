/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;

import DomainModels.SanPham;
import ViewModels.SanPhamViewModel;
import java.util.List;

/**
 *
 * @author admin
 */
public interface SanPhamRepository {
    
    List<SanPhamViewModel> getAll();
    SanPhamViewModel getOne(String ma);
    
    boolean add(SanPham sp);
    boolean update(SanPham sp);
    boolean delete(String maSP);
}
