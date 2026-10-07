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
import java.util.stream.Collectors;

/**
 *
 * @author ADMIN
 */
public class SizeServiceImpl implements SizeService{
    private SizeRepository SizeRep = new SizeRepositoryImpl();
    
    @Override
    public List<SizeViewModel> getAll(){
        return SizeRep.getAll();
    }
    
    @Override
    public List<SizeViewModel> search(String keyword) {
        String kw = keyword.toLowerCase();
        return SizeRep.getAll().stream()
                .filter(s -> s.getMaSize().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean add(Size s){
        return SizeRep.add(s);
    }
    
    @Override
    public boolean update(Size s, String maS){
        return SizeRep.update(s, maS);
    }
    
    public boolean delete(String maS){
        return SizeRep.delete(maS);
    }

    @Override
    public List<Size> getSizesBySPId(int spId) {
        return SizeRep.getSizesBySPId(spId);
    }
}
