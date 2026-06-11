/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import DomainModels.DanhMuc;
import java.util.List;


/**
 *
 * @author ADMIN
 */
public interface DanhMucRepository {
    List<DanhMuc> getAll();
    boolean add(DanhMuc dm);
    boolean update(DanhMuc dm, String maDM);
    boolean delete(String maDM);
}
