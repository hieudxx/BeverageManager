
package DomainModels;

/**
 *
 * @author Admin
 */
public class NhanVien {

    private String id;
    private String maNhanVien;
    private String tenDangNhap;
    private String matKhau;
    private String hoTen;
    private String vaiTro;
    private boolean trangThai;
    

    public NhanVien() {
    }

    public NhanVien(String id, String maNhanVien, String tenDangNhap, String hoTen, String vaiTro, boolean trangThai) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }
    
    

    public NhanVien(String id, String maNhanVien, String tenDangNhap, String matKhau, String hoTen, String vaiTro, boolean trangThai) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

}
