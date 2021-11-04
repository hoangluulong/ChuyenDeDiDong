package java.android.quanlybanhang.Model.KhachHang;

public class NhomKhachHang {
    String tenNhomKh;
    String maKH;
    String ghichuNhom;

    public NhomKhachHang() {
    }

    public NhomKhachHang(String tenNhomKh, String maKH, String ghichuNhom) {
        this.tenNhomKh = tenNhomKh;
        this.maKH = maKH;
        this.ghichuNhom = ghichuNhom;
    }

    public String getTenNhomKh() {
        return tenNhomKh;
    }

    public void setTenNhomKh(String tenNhomKh) {
        this.tenNhomKh = tenNhomKh;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getGhichuNhom() {
        return ghichuNhom;
    }

    public void setGhichuNhom(String ghichuNhom) {
        this.ghichuNhom = ghichuNhom;
    }
}
