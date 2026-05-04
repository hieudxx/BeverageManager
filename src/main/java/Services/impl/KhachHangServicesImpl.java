package Services.impl;

import DomainModels.KhachHang;
import Responsitories.IKhachHangRes;
import Responsitories.impl.KhachHangResImpl;
import Services.IKhachHangService;
import ViewModels.KhachHangViewModel;
import java.util.List;


public class KhachHangServicesImpl implements IKhachHangService{
    
    private final IKhachHangRes khRes = new KhachHangResImpl();

    @Override
    public List<KhachHangViewModel> getAll() {
        return khRes.getAll();
    }

    @Override
    public int add(KhachHang kh) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(String ma_kh, KhachHang kh) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(String ma_kh) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
