package java.android.quanlybanhang.function.CuaHangOnline.Data;

import java.util.ArrayList;

public class SanPhamQuangCao2

{
    private String id;
    private String chiTiet;
    private ArrayList<DonGia> donGia;
    private long giaNhap;
    private String imgProduct;
    private String nameProduct;
    private String nhomsanpham;
    private int soluong;
    private boolean status;

    public SanPhamQuangCao2() {
    }

    public SanPhamQuangCao2(String id, String chiTiet, ArrayList<DonGia> donGia, long giaNhap, String imgProduct, String nameProduct, String nhomsanpham, int soluong, boolean status) {
        this.id = id;
        this.chiTiet = chiTiet;
        this.donGia = donGia;
        this.giaNhap = giaNhap;
        this.imgProduct = imgProduct;
        this.nameProduct = nameProduct;
        this.nhomsanpham = nhomsanpham;
        this.soluong = soluong;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public ArrayList<DonGia> getDonGia() {
        return donGia;
    }

    public void setDonGia(ArrayList<DonGia> donGia) {
        this.donGia = donGia;
    }

    public long getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNhomsanpham() {
        return nhomsanpham;
    }

    public void setNhomsanpham(String nhomsanpham) {
        this.nhomsanpham = nhomsanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
