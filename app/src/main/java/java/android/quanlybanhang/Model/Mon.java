package java.android.quanlybanhang.Model;

public class Mon {
    String tensanpham;

    public Mon(String tensanpham, String soluong) {
        this.tensanpham = tensanpham;
        this.soluong = soluong;
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

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    String soluong;
}
