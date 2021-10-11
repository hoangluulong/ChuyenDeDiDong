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

    public boolean isAddToCart() {
        return addToCart;
    }

    public void setAddToCart(boolean addToCart) {
        this.addToCart = addToCart;
    }

    String nameProduct;

    public PushToFire(String nameProduct, int soluong, boolean addToCart) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
        this.addToCart = addToCart;
    }

    int soluong;
    boolean addToCart;
}
