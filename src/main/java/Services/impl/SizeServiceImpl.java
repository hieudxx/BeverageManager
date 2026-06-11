/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services.impl;

import DomainModels.Size;
import Repositories.impl.SizeRepositoryImpl;
import ViewModels.SizeViewModel;
import java.util.ArrayList;
import java.util.List;
import Repositories.SizeRepository;
import Services.SizeService;

/**
 *
 * @author ADMIN
 */
public class SizeServiceImpl implements SizeService{
    private SizeRepository SizeRep = new SizeRepositoryImpl();
    
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
    
    // Update
    @Override
    public boolean update(Size s, String maS){
        return SizeRep.update(s, maS);
    }
    
    // Delete
    public boolean delete(String maS){
        return SizeRep.delete(maS);
    }

    @Override
    public List<Size> getSizesBySPId(int spId) {
        return SizeRep.getSizesBySPId(spId);
    }
}
