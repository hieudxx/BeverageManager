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
public class HoaDonChiTiet {
    private int id;
    HoaDon hoaDon;
    Size size;
    private int soLuong;
    private BigDecimal giaLucBan;
    
    public HoaDonChiTiet(){
        
    }
    
    public HoaDonChiTiet(int id, HoaDon hoaDon, int soLuong, BigDecimal giaLucBan, Size size){
        this.id=id;
        this.hoaDon=hoaDon;
        this.size=size;
        this.soLuong=soLuong;
        this.giaLucBan=giaLucBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaLucBan() {
        return giaLucBan;
    }

    public void setGiaLucBan(BigDecimal giaLucBan) {
        this.giaLucBan = giaLucBan;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    
}
