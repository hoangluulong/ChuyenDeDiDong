package java.android.quanlybanhang;

public class PushToFire {
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

    public String getYeuCau() {
        return yeuCau;
    }

    public PushToFire(String nameProduct, int soluong ,String yeuCau) {
        this.nameProduct = nameProduct;
        this.yeuCau = yeuCau;
        this.soluong = soluong;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }



    public PushToFire(String nameProduct,int soluong) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
    }

    int soluong;
    boolean addToCart;
}
