/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModels;

import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class SanPham {
    private int id;
    private String maSanPham;
    DanhMuc danhMuc;
    private String tenSanPham;
    private BigDecimal giaCoBan;
    private String hinhAnh;
    private boolean dangBan;
    private boolean trangThaiHienThi;
    
    public SanPham(){
        
    }
    
    public SanPham(int id, String maSanPham, DanhMuc danhMuc, String tenSanPham, BigDecimal giaCoBan, String hinhAnh, boolean dangBan, boolean  trangThaiHienThi){
        this.id=id;
        this.maSanPham=maSanPham;
        this.danhMuc=danhMuc;
        this.tenSanPham=tenSanPham;
        this.giaCoBan=giaCoBan;
        this.hinhAnh=hinhAnh;
        this.dangBan=dangBan;
        this.trangThaiHienThi=trangThaiHienThi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public BigDecimal getGiaCoBan() {
        return giaCoBan;
    }

    public void setGiaCoBan(BigDecimal giaCoBan) {
        this.giaCoBan = giaCoBan;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public boolean isDangBan() {
        return dangBan;
    }

    public void setDangBan(boolean dangBan) {
        this.dangBan = dangBan;
    }

    public boolean isTrangThaiHienThi() {
        return trangThaiHienThi;
    }

    public void setTrangThaiHienThi(boolean trangThaiHienThi) {
        this.trangThaiHienThi = trangThaiHienThi;
    }
    
    
}
