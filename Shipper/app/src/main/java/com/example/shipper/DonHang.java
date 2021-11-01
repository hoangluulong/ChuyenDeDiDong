package com.example.shipper;

import java.io.Serializable;
import java.util.List;

public class DonHang implements Serializable {
    private int trangthai;

    private String diemnhan;

    private String diaChi;

    private long donGia;

    private List<SanPham> dsChon;

    private String tenKhachhang;

    private String sdtkhachhang;

    private String ghiChu;

    private long thunhap;

    public DonHang(String diemnhan, String diaChi, long donGia,String tenKhachhang,String sdtkhachhang, long thunhap, List<SanPham> dsChon) {
        this.diemnhan = diemnhan;
        this.diaChi = diaChi;
        this.donGia = donGia;
        this.tenKhachhang = tenKhachhang;
        this.sdtkhachhang = sdtkhachhang;
        this.thunhap = thunhap;
        this.dsChon = dsChon;
    }

    public DonHang() {
        this.diaChi = "dC";
    }

    public DonHang(int trangthai, String diemnhan, String diaChi, long donGia, List<SanPham> dsChon,
                   String tenKhachhang,String sdtkhachhang,String ghiChu, long thunhap) {
        this.trangthai = trangthai;

        this.diemnhan = diemnhan;
        this.diaChi = diaChi;
        this.donGia = donGia;
        this.dsChon=dsChon;
        this.tenKhachhang=tenKhachhang;
        this.sdtkhachhang=sdtkhachhang;
        this.ghiChu=ghiChu;
        this.thunhap=thunhap;
    }

    public List<SanPham> getDsChon() {
        return dsChon;
    }

    public void setDsChon(List<SanPham> dsChon) {
        this.dsChon = dsChon;
    }

    public String getTenKhachHang() {
        return tenKhachhang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachhang = tenKhachHang;
    }

    public String getSdtkhachhang() {
        return sdtkhachhang;
    }

    public void setSdtkhachhang(String sdtkhachhang) {
        this.sdtkhachhang = sdtkhachhang;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public String getDiemnhan() {
        return diemnhan;
    }

    public void setDiemnhan(String diemnhan) {
        this.diemnhan = diemnhan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public long getThunhap() {
        return thunhap;
    }

    public void setThunhap(long thunhap) {
        this.thunhap = thunhap;
    }

}
