package java.android.quanlybanhang.function.CuaHangOnline.Data;

import android.net.Uri;

public class SanPhamQuangCao {
    private String idCuahHang;
    private String key;
    private String tenSanPham;
    private int soLuong;
    private long giaBan;
    private long giamGia;
    private String nhomSp;
    private String moTa;
    private String imageName;
    private String imageUrl;
    private boolean thanhtoan;

    public SanPhamQuangCao() {
    }

    public SanPhamQuangCao(String idCuahHang, String key, String tenSanPham, int soLuong, long giaBan, long giamGia, String nhomSp, String moTa, String imageName, String imageUrl, boolean thanhtoan) {
        this.idCuahHang = idCuahHang;
        this.key = key;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giamGia = giamGia;
        this.nhomSp = nhomSp;
        this.moTa = moTa;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.thanhtoan = thanhtoan;
    }

    public String getNhomSp() {
        return nhomSp;
    }

    public void setNhomSp(String nhomSp) {
        this.nhomSp = nhomSp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getIdCuahHang() {
        return idCuahHang;
    }

    public void setIdCuahHang(String idCuahHang) {
        this.idCuahHang = idCuahHang;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isThanhtoan() {
        return thanhtoan;
    }

    public void setThanhtoan(boolean thanhtoan) {
        this.thanhtoan = thanhtoan;
    }
}
