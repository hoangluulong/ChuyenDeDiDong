package java.android.quanlybanhang.Model;

public class SanPhamTop {
    private String name;
    private int soLuong;
    private String color;
    //private ImageView imageView;

    public SanPhamTop() {
        this.color = "";
    }

    public SanPhamTop(String name, int soLuong, String color) {
        this.name = name;
        this.soLuong = soLuong;
        this.color = color;
    }

    public SanPhamTop(String name, int soLuong) {
        this.name = name;
        this.soLuong = soLuong;
        this.color = "";
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
