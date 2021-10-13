package java.android.quanlybanhang.Data;

import java.util.ArrayList;

public class Product {
    String id;
    String nameProduct;
    String chitiet;
    String Nhomsanpham;
    Double giaNhap;
    ArrayList<DonGia> donGia;
    int soluong;
    String imgProduct;
    boolean addToCart;
    String status;

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, ArrayList<DonGia> donGia, int soluong, String imgProduct, boolean addToCart, String status) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.donGia = donGia;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.addToCart = addToCart;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap,   ArrayList<DonGia> donGia, int soluong, String imgProduct) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.donGia = donGia;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
    }

    public ArrayList<DonGia> getDonGia() {
        return donGia;
    }

    public void setDonGia(ArrayList<DonGia> donGia) {
        this.donGia = donGia;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public boolean isAddToCart() {
        return addToCart;
    }

    public void setAddToCart(boolean addToCart) {
        this.addToCart = addToCart;
    }

    public Product(String nameProduct, int soluong, String imgProduct) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
    }



    public Product(String nameProduct, String chitiet, String nhomsanpham, Double giaNhap,  ArrayList<DonGia> donGia, int soluong) {
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.donGia = donGia;
        this.soluong = soluong;
    }

    public Product() {
    }

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap,  ArrayList<DonGia> donGia, int soluong) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.donGia = donGia;
        this.soluong = soluong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public String getNhomsanpham() {
        return Nhomsanpham;
    }

    public void setNhomsanpham(String nhomsanpham) {
        Nhomsanpham = nhomsanpham;
    }

    public Double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
