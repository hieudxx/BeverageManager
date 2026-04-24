/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class HoaDonChiTietViewModel {
    
    private int id;
    private String maHD;
    private String maSP;
    private String tenSP;
    private double donGia;
    private int soLuong;
    private BigDecimal thanhTien;
    
    public HoaDonChiTietViewModel(){
        
    }
        public HoaDonChiTietViewModel(int id, String maHD, String maSP, String tenSP, String donGia, int soLuong, BigDecimal thanhTien){
        this.id=id;
        this.maHD=maHD;
        this.maSP=maSP;
        this.tenSP=tenSP;
        this.donGia=this.donGia;
        this.soLuong=soLuong;
        this.thanhTien=thanhTien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }
        
}
