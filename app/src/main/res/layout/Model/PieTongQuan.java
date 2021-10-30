package java.android.quanlybanhang.Model;

public class PieTongQuan {
    private String name;
    private int soLuong;

    public PieTongQuan(){}

    public PieTongQuan(String name, int soLuong) {
        this.name = name;
        this.soLuong = soLuong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
