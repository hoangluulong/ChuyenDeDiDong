package java.android.quanlybanhang.Model;

public class BaoCaoKho {
    String nhanVien;
    String ngay;
    String gio;

    public BaoCaoKho() {
    }

    public BaoCaoKho(String nhanVien, String ngay, String gio) {
        this.nhanVien = nhanVien;
        this.ngay = ngay;
        this.gio = gio;
    }

    public String getNhanVien() { return nhanVien; }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

}
