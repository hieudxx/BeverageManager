/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import DomainModels.DanhMuc;
import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class SanPhamResponse {
    
    private int id;
    private String maSanPham;
    private String tenDanhMuc;
    private String tenSanPham;
    private BigDecimal giaCoBan;
    private String hinhAnh;
    private boolean dangBan;
    private boolean trangThaiHienThi;
    private String tenSize;
    private BigDecimal giaChenhLech;
    
    public SanPhamResponse(){
        
    }
    
    public SanPhamResponse(int id, String maSanPham, String tenDanhMuc, String tenSanPham, BigDecimal giaCoBan, String hinhAnh, boolean dangBan, boolean  trangThaiHienThi, String tenSize, BigDecimal giaChenhLech){
        this.id=id;
        this.maSanPham=maSanPham;
        this.tenDanhMuc=tenDanhMuc;
        this.tenSanPham=tenSanPham;
        this.giaCoBan=giaCoBan;
        this.hinhAnh=hinhAnh;
        this.dangBan=dangBan;
        this.trangThaiHienThi=trangThaiHienThi;
        this.tenSize=tenSize;
        this.giaChenhLech=giaChenhLech;
        
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

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
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

    public String getTenSize() {
        return tenSize;
    }

    public void setTenSize(String tenSize) {
        this.tenSize = tenSize;
    }

    public BigDecimal getGiaChenhLech() {
        return giaChenhLech;
    }

    public void setGiaChenhLech(BigDecimal giaChenhLech) {
        this.giaChenhLech = giaChenhLech;
    }
    
    @Override
    public String toString() {
        return this.maSanPham; // hoặc getMaSanPham()
    }
    
}
