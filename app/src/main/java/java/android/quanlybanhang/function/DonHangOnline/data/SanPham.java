package java.android.quanlybanhang.function.DonHangOnline.data;

public class SanPham {
    private String yeuCau;
    private Double giaProudct;
    private int soluong;
    private String nameProduct;
    private String imgProduct;

    public SanPham() {
    }

    public SanPham(String yeuCau, Double giaProudct, int soluong, String nameProduct, String imgProduct) {
        this.yeuCau = yeuCau;
        this.giaProudct = giaProudct;
        this.soluong = soluong;
        this.nameProduct = nameProduct;
        this.imgProduct = imgProduct;
    }

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    public Double getGiaProudct() {
        return giaProudct;
    }

    public void setGiaProudct(Double giaProudct) {
        this.giaProudct = giaProudct;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }
}
