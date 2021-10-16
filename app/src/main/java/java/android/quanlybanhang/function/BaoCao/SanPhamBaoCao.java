package java.android.quanlybanhang.function.BaoCao;

public class SanPhamBaoCao {
    private String tensanpham;
    private String nhomsanpham;
    private Double thanhtien;
    private int soluong;
    private Double giatien;
    private String color;

    public SanPhamBaoCao() {
    }

    public SanPhamBaoCao(String tensanpham, String nhomsanpham, Double thanhtien, Double giatien, int soluong) {
        this.tensanpham = tensanpham;
        this.nhomsanpham = nhomsanpham;
        this.thanhtien = thanhtien;
        this.giatien = giatien;
        this.soluong = soluong;
        this.color = "";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getNhomsanpham() {
        return nhomsanpham;
    }

    public void setNhomsanpham(String nhomsanpham) {
        this.nhomsanpham = nhomsanpham;
    }

    public Double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(Double thanhtien) {
        this.thanhtien = thanhtien;
    }

    public Double getGiatien() {
        return giatien;
    }

    public void setGiatien(Double giatien) {
        this.giatien = giatien;
    }
}
