/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import ViewModels.SanPhamResponse;
import java.util.List;

public interface SanPhamServices {
    List<SanPhamResponse> getAll();
    SanPhamResponse getOne(String ma);
}
