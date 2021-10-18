package java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan;

public class StaticBanModel {


    public StaticBanModel(String ID, String tenban, String trangthai, String tenNhanVien, String gioDaOder) {
        this.ID = ID;
        this.tenban = tenban;
        this.trangthai = trangthai;
        this.tenNhanVien = tenNhanVien;
        this.gioDaOder = gioDaOder;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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

    private String ID ;
    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public String getGioDaOder() {
        return gioDaOder;
    }
    public String getTenban() {
        return tenban;
    }

    public String getTrangthai() {
        return trangthai;
    }

    private   String tenban;

    public StaticBanModel(String tenban, String trangthai, String tenNhanVien, String gioDaOder) {
        this.tenban = "";
        this.trangthai = "";
        this.tenNhanVien = "";
        this.gioDaOder = "";
    }

    public StaticBanModel() {
    }

    private   String trangthai;

    public StaticBanModel(String tenban, String trangthai) {
        this.tenban = tenban;
        this.trangthai = trangthai;
    }

    private  String tenNhanVien;
    private  String gioDaOder;


}
