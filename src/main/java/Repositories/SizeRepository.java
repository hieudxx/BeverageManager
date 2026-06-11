/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import DomainModels.Size;
import ViewModels.SizeViewModel;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface SizeRepository {
    List<SizeViewModel> getAll();
    boolean add(Size s);
    boolean update(Size s, String maS);
    boolean delete(String maS);
    List<Size> getSizesBySPId(int spId);
} 
