package java.android.quanlybanhang.Data;

public class Ban {
    private   String tenban;
    private   String trangthai;
    private  String tenNhanVien;
    private  String gioDaOder;


    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public void setGioDaOder(String gioDaOder) {
        this.gioDaOder = gioDaOder;
    }

    public String getTenban() {
        return tenban;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public String getGioDaOder() {
        return gioDaOder;
    }

    public Ban(String tenban, String trangthai, String tenNhanVien, String gioDaOder) {
        this.tenban = tenban;
        this.trangthai = trangthai;
        this.tenNhanVien = tenNhanVien;
        this.gioDaOder = gioDaOder;
    }

    public Ban(String tenban, String trangthai) {
        this.tenban = tenban;
        this.trangthai = trangthai;
    }
}
