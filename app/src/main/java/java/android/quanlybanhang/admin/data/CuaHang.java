package java.android.quanlybanhang.admin.data;

public class CuaHang {
    private String tenCuaHang;
    private String ID_CUAHANG;
    private String soDienThoai;

    public CuaHang(String tenCuaHang, String ID_CUAHANG) {
        this.tenCuaHang = tenCuaHang;
        this.ID_CUAHANG = ID_CUAHANG;
    }

    public CuaHang(String tenCuaHang, String ID_CUAHANG, String soDienThoai) {
        this.tenCuaHang = tenCuaHang;
        this.ID_CUAHANG = ID_CUAHANG;
        this.soDienThoai = soDienThoai;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTenCuaHang() {
        return tenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
    }

    public String getID_CUAHANG() {
        return ID_CUAHANG;
    }

    public void setID_CUAHANG(String ID_CUAHANG) {
        this.ID_CUAHANG = ID_CUAHANG;
    }
}
