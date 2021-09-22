package java.android.quanlybanhang.Data;

public class KhuVuc {

    private String tenkhuvuc;
    private  String trangthai;

    public KhuVuc(String tenkhuvuc, String trangthai) {
        this.tenkhuvuc = tenkhuvuc;
        this.trangthai = trangthai;
    }

    public void setTenkhuvuc(String tenkhuvuc) {
        this.tenkhuvuc = tenkhuvuc;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getTenkhuvuc() {
        return tenkhuvuc;
    }

    public String getTrangthai() {
        return trangthai;
    }
}
