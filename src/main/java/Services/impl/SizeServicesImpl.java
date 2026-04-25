/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.Size;
import Responsitories.SizeResponsitories;
import Responsitories.impl.SizeResponsitoriesImpl;
import Services.SizeServices;
import ViewModels.SizeViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class SizeServicesImpl implements SizeServices{
    private SizeResponsitories SizeRep = new SizeResponsitoriesImpl();
    
    // List
    @Override
    public List<SizeViewModel> getAll(){
        return SizeRep.getAll();
    }
    
    // Search
    @Override
    public List<SizeViewModel> search(String keyword){
        List<SizeViewModel> lists = SizeRep.getAll();
        List<SizeViewModel> results = new ArrayList<>();
        for(SizeViewModel size : lists){
            if(size.getMaSize().toLowerCase().contains(keyword.toLowerCase())){
                SizeViewModel vm = new SizeViewModel();
                vm.setMaSize(size.getMaSize());
                results.add(vm);
            }
        }
        return results;
    }
    
    // Add
    @Override
    public boolean add(Size s){
        return SizeRep.add(s);
    }
}
