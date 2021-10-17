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

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public PushToFire(String nameProduct, String yeuCau, String imgProduct, String giaProduct, int soluong, boolean addToCart) {
        this.nameProduct = nameProduct;
        this.yeuCau = yeuCau;
        this.imgProduct = imgProduct;
        this.giaProduct = giaProduct;
        this.soluong = soluong;
        this.addToCart = addToCart;
    }

    public String getGiaProduct() {
        return giaProduct;
    }

    public void setGiaProduct(String giaProduct) {
        this.giaProduct = giaProduct;
    }

    public boolean isAddToCart() {
        return addToCart;
    }

    public void setAddToCart(boolean addToCart) {
        this.addToCart = addToCart;
    }

    String imgProduct;
    String giaProduct;


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
