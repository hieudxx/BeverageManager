/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author ADMIN
 */
public class DanhMucViewModel {
    private int id;
    private String maDanhMuc;
    private String tenDanhMuc;
    private boolean trangThaiHienThi;
    
    public DanhMucViewModel(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public boolean isTrangThaiHienThi() {
        return trangThaiHienThi;
    }

    public void setTrangThaiHienThi(boolean trangThaiHienThi) {
        this.trangThaiHienThi = trangThaiHienThi;
    }
}
