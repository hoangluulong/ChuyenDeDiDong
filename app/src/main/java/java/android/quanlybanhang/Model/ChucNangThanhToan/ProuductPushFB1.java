package java.android.quanlybanhang.Model.ChucNangThanhToan;

public class ProuductPushFB1 {
    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }



    String nameProduct;
    String yeuCau;
    String imgProduct;
    Double giaProudct;
    int soluong;


    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public ProuductPushFB1(String nameProduct, String yeuCau, String imgProduct, Double giaProduct, int soluong) {
        this.nameProduct = nameProduct;
        this.yeuCau = yeuCau;
        this.imgProduct = imgProduct;
        this.giaProudct = giaProduct;
        this.soluong = soluong;

    }







    public Double getGiaProudct() {
        return giaProudct;
    }

    public void setGiaProudct(Double giaProudct) {
        this.giaProudct = giaProudct;
    }




    public String getYeuCau() {
        return yeuCau;
    }

    public ProuductPushFB1(String nameProduct, int soluong , String yeuCau) {
        this.nameProduct = nameProduct;
        this.yeuCau = yeuCau;
        this.soluong = soluong;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }



    public ProuductPushFB1(String nameProduct, int soluong) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
    }


}
