package java.android.quanlybanhang.function.DonHangOnline.data;

import java.util.ArrayList;
import java.util.Date;

public class DonHang {
    private String diaChi;
    private String time;
    private int trangthai;
    private String idKhachhang;
    private Double giaKhuyenMai;
    private ArrayList<SanPham> sanpham;
    private double donGia;
    private String key;
    private Date date;
    private String tenKhachHang;

    public DonHang() {
    }

    public DonHang(String diaChi, String time, int trangthai, String idKhachhang, Double giaKhuyenMai, ArrayList<SanPham> sanpham) {
        this.diaChi = diaChi;
        this.time = time;
        this.trangthai = trangthai;
        this.idKhachhang = idKhachhang;
        this.giaKhuyenMai = giaKhuyenMai;
        this.sanpham = sanpham;
        this.donGia = 0;
        this.key = "";
        this.date = null;
    }

    public DonHang(String diaChi, String time, int trangthai, String idKhachhang, Double giaKhuyenMai, ArrayList<SanPham> sanpham, double donGia, String key, Date date, String tenKhachHang) {
        this.diaChi = diaChi;
        this.time = time;
        this.trangthai = trangthai;
        this.idKhachhang = idKhachhang;
        this.giaKhuyenMai = giaKhuyenMai;
        this.sanpham = sanpham;
        this.donGia = donGia;
        this.key = key;
        this.date = date;
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getIdKhachhang() {
        return idKhachhang;
    }

    public void setIdKhachhang(String idKhachhang) {
        this.idKhachhang = idKhachhang;
    }

    public Double getGiaKhuyenMai() {
        return giaKhuyenMai;
    }

    public void setGiaKhuyenMai(Double giaKhuyenMai) {
        this.giaKhuyenMai = giaKhuyenMai;
    }

    public ArrayList<SanPham> getSanpham() {
        return sanpham;
    }

    public void setSanpham(ArrayList<SanPham> sanpham) {
        this.sanpham = sanpham;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
