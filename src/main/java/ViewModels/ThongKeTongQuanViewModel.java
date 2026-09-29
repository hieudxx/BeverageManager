/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import java.math.BigDecimal;

public class ThongKeTongQuanViewModel {
    
    private BigDecimal tongDoanhThu;
    private int tongHoaDon;
    private String monBanChayNhat;
    private int soLuongMonBanChayNhat;

    public ThongKeTongQuanViewModel() {
        this.tongDoanhThu = BigDecimal.ZERO;
        this.tongHoaDon = 0;
        this.monBanChayNhat = "Chưa có dữ liệu";
        this.soLuongMonBanChayNhat = 0;
    }

    public BigDecimal getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(BigDecimal tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu == null ? BigDecimal.ZERO : tongDoanhThu;
    }

    public int getTongHoaDon() {
        return tongHoaDon;
    }

    public void setTongHoaDon(int tongHoaDon) {
        this.tongHoaDon = tongHoaDon;
    }

    public String getMonBanChayNhat() {
        return monBanChayNhat;
    }

    public void setMonBanChayNhat(String monBanChayNhat) {
        this.monBanChayNhat = (monBanChayNhat == null || monBanChayNhat.isBlank())
                ? "Chưa có dữ liệu" : monBanChayNhat;
    }

    public int getSoLuongMonBanChayNhat() {
        return soLuongMonBanChayNhat;
    }

    public void setSoLuongMonBanChayNhat(int soLuongMonBanChayNhat) {
        this.soLuongMonBanChayNhat = soLuongMonBanChayNhat;
    }
}
