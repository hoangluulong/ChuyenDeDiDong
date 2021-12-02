package java.android.quanlybanhang.Model;

public class LichSuHoatDong {
    private String congViec;
    private String nhanVien;
    private String thoiGian;

    public LichSuHoatDong() {
    }

    public LichSuHoatDong(String congViec, String nhanVien, String thoiGian) {
        this.congViec = congViec;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
    }

    public String getCongViec() {
        return congViec;
    }

    public void setCongViec(String congViec) {
        this.congViec = congViec;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
