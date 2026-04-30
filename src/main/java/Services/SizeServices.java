/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DomainModels.Size;
import ViewModels.SizeViewModel;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface SizeServices {
    List<SizeViewModel> getAll();
    List<SizeViewModel> search(String keyword);
    public boolean add(Size s);
    public boolean update(Size s, String maS);
    public boolean delete(String maS);
    List<Size> getSizesBySPId(int spId);
}
