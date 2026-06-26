/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.DanhMuc;
import ViewModels.DanhMucViewModel;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface DanhMucService {

    List<DanhMuc> getAll();
    public boolean add(DanhMuc dm);
    public boolean update(DanhMuc dm, String maDM);
    public boolean delete(String maDM);
    List<DanhMucViewModel> getAllView();
    List<DanhMucViewModel> search(String keyword);
    List<DanhMucViewModel> getAllByTrangThai();
}
