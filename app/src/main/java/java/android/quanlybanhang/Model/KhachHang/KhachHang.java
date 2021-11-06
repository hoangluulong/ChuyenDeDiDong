package java.android.quanlybanhang.Model.KhachHang;

public class KhachHang {
    String hoTenKhachHang;
    String soDT;
    String diaChi;
    String nhomKhachKhach;
    String gioiTinh;
    String email;
    String ghiChu;
    String ngaySinh;

    public KhachHang() {
    }

    public KhachHang(String hoTenKhachHang, String soDT, String diaChi, String nhomKhachKhach, String gioiTinh, String email, String ghiChu,String ngaySinh) {
        this.hoTenKhachHang = hoTenKhachHang;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.nhomKhachKhach = nhomKhachKhach;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.ghiChu = ghiChu;
        this.ngaySinh = ngaySinh;
    }

    public String getHoTenKhachHang() {
        return hoTenKhachHang;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setHoTenKhachHang(String hoTenKhachHang) {
        this.hoTenKhachHang = hoTenKhachHang;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNhomKhachKhach() {
        return nhomKhachKhach;
    }

    public void setNhomKhachKhach(String nhomKhachKhach) {
        this.nhomKhachKhach = nhomKhachKhach;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
