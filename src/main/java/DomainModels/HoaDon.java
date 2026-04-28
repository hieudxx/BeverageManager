/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModels;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author admin
 */
public class HoaDon {
    
    private int id;
    private String maHoaDon;
    private LocalDateTime ngayTao;
    NhanVien nhanVien;
    KhachHang khachHang;
    private BigDecimal tongTien;
    private BigDecimal tienThanhToan;
    private String phuongThucTT;
    private String trangThai;
    
    public HoaDon(){
        
    }
    public HoaDon(int id, String maHoaDon, LocalDateTime ngayTao, NhanVien nhanVien, KhachHang khachHang, BigDecimal tongTien, BigDecimal tienThanhToan, String phuongThucTT, String trangThai){
        this.id=id;
        this.maHoaDon=maHoaDon;
        this.ngayTao=ngayTao;
        this.nhanVien=nhanVien;
        this.tongTien=tongTien;
        this.tienThanhToan=tienThanhToan;
        this.phuongThucTT=phuongThucTT;
        this.trangThai=trangThai;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public BigDecimal getTienThanhToan() {
        return tienThanhToan;
    }

    public void setTienThanhToan(BigDecimal tienThanhToan) {
        this.tienThanhToan = tienThanhToan;
    }

    public String getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(String phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
    
}
