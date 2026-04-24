/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Responsitories;

import DomainModels.SanPham;
import ViewModels.SanPhamResponse;
import java.util.List;

/**
 *
 * @author admin
 */
public interface SanPhamResponsitories {
    
    List<SanPhamResponse> getAll();
    SanPhamResponse getOne(String ma);
    
    boolean add(SanPham sp);
    boolean update(SanPham sp);
    boolean delete(String maSP);
}
