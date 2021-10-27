package java.android.quanlybanhang.function.BepBar.Data;

public class Mon {
    String nameProduct;

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    String yeuCau;

    public Mon(String nameProduct, long soluong,String yeuCau) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
        this.yeuCau=yeuCau;
    }
    public Mon()
    {

    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getSoluong() {
        return soluong;
    }

    public void setSoluong(long soluong) {
        this.soluong = soluong;
    }

    long soluong;
}
