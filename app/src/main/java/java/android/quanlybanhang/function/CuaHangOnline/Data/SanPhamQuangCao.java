package java.android.quanlybanhang.function.CuaHangOnline.Data;

import android.net.Uri;

public class SanPhamQuangCao {
    private String tenSanPham;
    private int soLuong;
    private long giaBan;
    private long giamGia;
    private String moTa;
    private Uri urlImage;

    public SanPhamQuangCao() {
    }

    public SanPhamQuangCao(String tenSanPham, int soLuong, long giaBan, long giamGia, String moTa, Uri urlImage) {
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giamGia = giamGia;
        this.moTa = moTa;
        this.urlImage = urlImage;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(long giaBan) {
        this.giaBan = giaBan;
    }

    public long getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(long giamGia) {
        this.giamGia = giamGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Uri getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(Uri urlImage) {
        this.urlImage = urlImage;
    }
}
