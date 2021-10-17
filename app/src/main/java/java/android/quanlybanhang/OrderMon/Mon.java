package java.android.quanlybanhang.OrderMon;

public class Mon {
    String tensanpham;

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    String yeuCau;

    public Mon(String nameProduct, long soluong,String yeuCau) {
        this.tensanpham = nameProduct;
        this.soluong = soluong;
        this.yeuCau=yeuCau;
    }
    public Mon()
    {

    }
    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public long getSoluong() {
        return soluong;
    }

    public void setSoluong(long soluong) {
        this.soluong = soluong;
    }

    long soluong;
}
