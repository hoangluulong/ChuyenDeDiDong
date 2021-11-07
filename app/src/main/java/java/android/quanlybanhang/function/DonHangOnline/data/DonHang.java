package java.android.quanlybanhang.function.DonHangOnline.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DonHang {
    private String diaChi;
    private String diemnhan;
    private String time;
    private int trangthai;
    private String idKhachhang;
    private Double giaKhuyenMai;
    private ArrayList<SanPham> sanpham;
    private double donGia;
    private String key;
    private Date date;
//    private String tenKhachHang;
    private String tenKhachhang;
    private String nhanVien;
    private String shipper;
    private String phoneShipper;
    private int phuongThucThanhToan;
    private String idQuan;

    public DonHang() { }

    public DonHang(String diaChi, String time, int trangthai, String idKhachhang, Double giaKhuyenMai, ArrayList<SanPham> sanpham, int phuongThucThanhToan) {
        this.diaChi = diaChi;
        this.time = time;
        this.trangthai = trangthai;
        this.idKhachhang = idKhachhang;
        this.giaKhuyenMai = giaKhuyenMai;
        this.sanpham = sanpham;
        this.donGia = 0;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.key = "";
        this.date = null;
        this.tenKhachhang = "";
        this.nhanVien = "";
        this.phoneShipper = "";
        this.shipper = "";
        this.diemnhan = "123 Trần Duy Hưng";
    }

    public DonHang(String diaChi, String time, int trangthai, String idKhachhang, Double giaKhuyenMai, ArrayList<SanPham> sanpham, double donGia, String key, Date date, String tenKhachhang, int phuongThucThanhToan) {
        this.diaChi = diaChi;
        this.time = time;
        this.trangthai = trangthai;
        this.idKhachhang = idKhachhang;
        this.giaKhuyenMai = giaKhuyenMai;
        this.sanpham = sanpham;
        this.donGia = donGia;
        this.key = key;
        this.date = date;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tenKhachhang = tenKhachhang;
        this.nhanVien = "";
        this.phoneShipper = "";
        this.shipper = "";
        this.diemnhan = "123 Trần Duy Hưng";
    }

    public DonHang(String diaChi, String time, int trangthai, String idKhachhang, Double giaKhuyenMai, ArrayList<SanPham> sanpham, double donGia, String key, Date date, String tenKhachhang, String nhanVien, int phuongThucThanhToan) {
        this.diaChi = diaChi;
        this.time = time;
        this.trangthai = trangthai;
        this.idKhachhang = idKhachhang;
        this.giaKhuyenMai = giaKhuyenMai;
        this.sanpham = sanpham;
        this.donGia = donGia;
        this.key = key;
        this.date = date;
        this.tenKhachhang = tenKhachhang;
        this.nhanVien = nhanVien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.phoneShipper = "";
        this.shipper = "";
        this.diemnhan = "123 Trần Duy Hưng";
    }

    public DonHang(String diaChi, String time, int trangthai, String idKhachhang, Double giaKhuyenMai, ArrayList<SanPham> sanpham, double donGia, String key, Date date, String tenKhachhang, String nhanVien, String shipper, String phoneShipper, int phuongThucThanhToan) {
        this.diaChi = diaChi;
        this.time = time;
        this.trangthai = trangthai;
        this.idKhachhang = idKhachhang;
        this.giaKhuyenMai = giaKhuyenMai;
        this.sanpham = sanpham;
        this.donGia = donGia;
        this.key = key;
        this.date = date;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tenKhachhang = tenKhachhang;
        this.nhanVien = nhanVien;
        this.shipper = shipper;
        this.phoneShipper = phoneShipper;
        this.diemnhan = "123 Trần Duy Hưng";
    }

    public DonHang(String diaChi, String diemnhan, String time, int trangthai, String idKhachhang, Double giaKhuyenMai, ArrayList<SanPham> sanpham, double donGia, String key, Date date, String tenKhachhang, String nhanVien, String shipper, String phoneShipper, int phuongThucThanhToan, String idQuan) {
        this.diaChi = diaChi;
        this.diemnhan = diemnhan;
        this.time = time;
        this.trangthai = trangthai;
        this.idKhachhang = idKhachhang;
        this.giaKhuyenMai = giaKhuyenMai;
        this.sanpham = sanpham;
        this.donGia = donGia;
        this.key = key;
        this.date = date;
        this.tenKhachhang = tenKhachhang;
        this.nhanVien = nhanVien;
        this.shipper = shipper;
        this.phoneShipper = phoneShipper;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.idQuan = idQuan;
    }

    public String getIdQuan() {
        return idQuan;
    }

    public void setIdQuan(String idQuan) {
        this.idQuan = idQuan;
    }

    public String getDiemnhan() {
        return diemnhan;
    }

    public void setDiemnhan(String diemnhan) {
        this.diemnhan = diemnhan;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getPhoneShipper() {
        return phoneShipper;
    }

    public void setPhoneShipper(String phoneShipper) {
        this.phoneShipper = phoneShipper;
    }

    public String getTenKhachhang() {
        return tenKhachhang;
    }

    public void setTenKhachhang(String tenKhachhang) {
        this.tenKhachhang = tenKhachhang;
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

    public int getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(int phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

}
