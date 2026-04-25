/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import DomainModels.SanPham;
import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class SizeViewModel {
 private int id;
    private String maSize;
    private SanPham SanPham;
    private String tenSizel;
    private BigDecimal giaChenhLech;
    private boolean trangThaiHienThi;
    
    public SizeViewModel(){
        
    }

    public SizeViewModel(int id, String maSize, SanPham SanPham, String tenSizel, BigDecimal giaChenhLech, boolean trangThaiHienThi) {
        this.id = id;
        this.maSize = maSize;
        this.SanPham = SanPham;
        this.tenSizel = tenSizel;
        this.giaChenhLech = giaChenhLech;
        this.trangThaiHienThi = trangThaiHienThi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSize() {
        return maSize;
    }

    public void setMaSize(String maSize) {
        this.maSize = maSize;
    }

    public SanPham getSanPham() {
        return SanPham;
    }

    public void setSanPham(SanPham SanPham) {
        this.SanPham = SanPham;
    }

    public String getTenSizel() {
        return tenSizel;
    }

    public void setTenSizel(String tenSizel) {
        this.tenSizel = tenSizel;
    }

    public BigDecimal getGiaChenhLech() {
        return giaChenhLech;
    }

    public void setGiaChenhLech(BigDecimal giaChenhLech) {
        this.giaChenhLech = giaChenhLech;
    }

    public boolean isTrangThaiHienThi() {
        return trangThaiHienThi;
    }

    public void setTrangThaiHienThi(boolean trangThaiHienThi) {
        this.trangThaiHienThi = trangThaiHienThi;
    }

}
