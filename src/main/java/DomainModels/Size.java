/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModels;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class Size {
    private int id;
    private String maSize;
    SanPham SanPham;
    private String tenSize;
    private BigDecimal giaChenhLech;
    private boolean trangThaiHienThi;
    
    public Size(){
        
    }

    public Size(int id, String maSize, SanPham SanPham, String tenSize, BigDecimal giaChenhLech, boolean trangThaiHienThi) {
        this.id = id;
        this.maSize = maSize;
        this.SanPham = SanPham;
        this.tenSize = tenSize;
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

    public boolean isTrangThaiHienThi() {
        return trangThaiHienThi;
    }

    public void setTrangThaiHienThi(boolean trangThaiHienThi) {
        this.trangThaiHienThi = trangThaiHienThi;
    }

}
