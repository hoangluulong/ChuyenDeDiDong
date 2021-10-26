package java.android.quanlybanhang.Model;

public class SanPhamTop {
    private String name;
    private int soLuong;
    //private ImageView imageView;

    public SanPhamTop() {
    }

//    public SanPhamTop(String name, int soLuong, ImageView imageView) {
//        this.name = name;
//        this.soLuong = soLuong;
//        this.imageView = imageView;
//    }

    public SanPhamTop(String name, int soLuong) {
        this.name = name;
        this.soLuong = soLuong;
        //this.imageView = imageView;
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

//    public ImageView getImageView() {
//        return imageView;
//    }
//
//    public void setImageView(ImageView imageView) {
//        this.imageView = imageView;
//    }
}
