package ViewModels;

public class NhanVienViewModel {

    private int id;
    private String maNhanVien;
    private String tenDangNhap;
    private String matKhau;
    private String hoTen;
    private String vaiTro;
    private String trangThai;

    public NhanVienViewModel() {
        
        
    }

    public NhanVienViewModel(int id, String maNhanVien, String tenDangNhap, String hoTen, String vaiTro, String trangThai) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public NhanVienViewModel(int id, String maNhanVien, String tenDangNhap, String matKhau, String hoTen, String vaiTro, String trangThai) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    
    

}
