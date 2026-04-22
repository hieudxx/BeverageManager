/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModels;

/**
 *
 * @author admin
 */
public class KhachHang {
    
    private int id;
    private String maKhachHang;
    private String soDienThoai;
    private String hoTen;
    
    public KhachHang(){
        
    }
    
    public KhachHang(int id, String maKhachHang, String soDienThoai, String hoTen){
        this.id=id;
        this.maKhachHang=maKhachHang;
        this.soDienThoai=soDienThoai;
        this.hoTen=hoTen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    
}
