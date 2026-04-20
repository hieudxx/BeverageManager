/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.DanhMuc;
import Responsitories.DanhMucRepository;
import Services.DanhMucServices;
import java.util.ArrayList;
/**
 *
 * @author ADMIN
 */
public class DanhMucServicesImpl implements DanhMucServices{
    private DanhMucRepository dmr = new DanhMucRepository();
    
    @Override
    public ArrayList<DanhMuc> getAllDanhMuc(){
        return dmr.getAllDanhMuc();
    }
}
