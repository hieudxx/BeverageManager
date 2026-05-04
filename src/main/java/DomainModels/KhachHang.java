package DomainModels;

public class KhachHang {

    private int id;
    private String maKhachHang;
    private String soDienThoai;
    private String hoTen;
    private boolean trangThai;

    public KhachHang() {

    }

    public KhachHang(int id, String maKhachHang, String soDienThoai, String hoTen, boolean trangThai) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.soDienThoai = soDienThoai;
        this.hoTen = hoTen;
        this.trangThai = trangThai;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
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
