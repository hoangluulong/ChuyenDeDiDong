package java.android.quanlybanhang.function.CuaHangOnline.Data;

public class ThongTinCuaHang {
    private String name;
    private String soDienThoai;
    private String moTa;

    public ThongTinCuaHang() {
    }

    public ThongTinCuaHang(String name, String soDienThoai, String moTa) {
        this.name = name;
        this.soDienThoai = soDienThoai;
        this.moTa = moTa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
